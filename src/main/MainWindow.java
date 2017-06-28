package main;


import controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Stage window = primaryStage;

        loadLoginScene(window);

    }


    private void loadLoginScene(Stage window) throws IOException{
        URL path = getClass().getClassLoader().getResource("view/loginWindow.fxml");
        FXMLLoader loader = new FXMLLoader(path);

        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setWindow(window); // sending window to login controller

        Scene scene = new Scene(root, 300, 200);

        window.setTitle("Login");
        window.setScene(scene);
        window.show();
    }

}
