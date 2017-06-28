package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Manager;
import model.User;
import model.dto.UserDTO;
import retrofit2.Call;
import retrofit2.Response;

import javax.jws.WebParam;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private Stage window;

    private ObservableList<String> roles = FXCollections.observableArrayList("ADMIN", "USER");
    private UserDTO user;

    @FXML private TextField textFieldUserId;
    @FXML private TextField textFieldUserEmail;
    @FXML private PasswordField passwordFieldUserPassword;
    @FXML private TextField textFieldUserName;
    @FXML private TextField textFieldUserAddress;
    @FXML private TextField textFieldUserPhone;
    @FXML private ChoiceBox choiceBoxRole;
    @FXML private CheckBox checkBoxUserActive;
    @FXML private Button buttonSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choiceBoxRole.setItems(roles);

        if(user != null){
            textFieldUserId.setText(String.valueOf(user.getId()));
            textFieldUserEmail.setText(user.getEmail());
            textFieldUserName.setText(user.getName());
            textFieldUserAddress.setText(user.getAddress());
            textFieldUserPhone.setText(user.getPhone());

            if(user.isActive()){
                checkBoxUserActive.setSelected(true);
            }else{
                checkBoxUserActive.setSelected(false);
            }

            if(user.getRole().equals("ADMIN")){
                choiceBoxRole.getSelectionModel().select(0);
            }else{
                choiceBoxRole.getSelectionModel().select(1);
            }
        }else{
            buttonSave.setText("Add");
        }


    }


    public void setUser(UserDTO user){
        this.user = user;
    }

    public void saveUserDetails(){

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(textFieldUserEmail.getText());
        userDTO.setName(textFieldUserName.getText());
        userDTO.setAddress(textFieldUserAddress.getText());
        userDTO.setPhone(textFieldUserPhone.getText());
        userDTO.setRole((String) choiceBoxRole.getSelectionModel().getSelectedItem());
        userDTO.setActive(checkBoxUserActive.isSelected());

        String password = passwordFieldUserPassword.getText();
        if(!password.isEmpty()){
            userDTO.setPassword(password);
        }

        boolean status;
        if(user == null){
            status = addUser(userDTO);
        }else {
            userDTO.setId(user.getId());
            status = editUser(userDTO);
        }

        if(status){
            // just close window
            window.close();
        }else{
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").showAndWait();
        }

    }


    private boolean addUser(UserDTO user){
        boolean status = false;

        Response response;
        try {
            response = Manager
                    .userApiService
                    .addUser(user, Manager.token).execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 201){
            return true;
        }

        return status;
    }

    private boolean editUser(UserDTO user){
        boolean status = false;
        Response response = null;

        try {
            response = Manager.userApiService
                    .editUser(user, user.getId(),Manager.token)
                    .execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 200){
            status = true;
        }

        return status;
    }

    public void setWindow(Stage window){
        this.window = window;
    }

}
