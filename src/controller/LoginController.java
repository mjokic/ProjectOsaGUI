package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.LoginCredentials;
import model.Manager;
import retrofit2.Response;

import java.net.URL;

public class LoginController {

    private Stage window;

    public TextField email;
    public TextField password;
    public Button loginButton;
    public Label wrongDetails;


    public void loginAction() throws Exception{

        String email = this.email.getText();
        String password = this.password.getText();

        LoginCredentials credentials = new LoginCredentials(email, password, null);
        boolean status = sendLoginRequest(credentials);

        if(status) {
            URL path = getClass().getClassLoader().getResource("view/mainWindow.fxml");
            FXMLLoader loader = new FXMLLoader(path);
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 650, 500);
            window.setTitle("Welcome - " + email);
            window.setScene(scene);

            MainController mainController = loader.getController();
            mainController.setWindow(window); // sending window to main controller

        }else{
            this.wrongDetails.setText("Wrong email/password!");
        }
    }

    public void setWindow(Stage window){
        this.window = window;
    }


    private boolean sendLoginRequest(LoginCredentials credentials) throws Exception{

        Response response = Manager.userApiService
                .login(credentials)
                .execute();

        if(response.code() == 200){
            Manager.email = credentials.getEmail();
            Manager.token = response.headers().get("Authorization");
            return true;
        }

        return false;

    }


}
