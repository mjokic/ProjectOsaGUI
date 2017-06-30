package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Manager;
import model.dto.UserDTO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private Stage window;

    private ObservableList<String> roles = FXCollections.observableArrayList("ADMIN", "USER");
    private UserDTO user;

    private String userAvatar = "default.png";

    @FXML private ImageView imageViewUserAvatar;
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

            this.userAvatar = user.getPicture();
            loadAvatar(user.getPicture());

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
            loadAvatar(this.userAvatar);
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
        userDTO.setPicture(this.userAvatar);

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

    public void choseAvatar(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose avatar");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg","*.png"));

        File file = fileChooser.showOpenDialog(window);

        if(file != null) {
            System.out.println(file.getName());

            boolean status;
            try {
                status = uploadAvatar(file);
            }catch (Exception ex){
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                return;
            }

            if(status){
                loadAvatar(this.userAvatar);
                new Alert(Alert.AlertType.INFORMATION, "Upload successful!").showAndWait();
            }else{
                new Alert(Alert.AlertType.ERROR, "Upload unsuccessful!").showAndWait();
            }
        }


    }

    private void loadAvatar(String avatar){
        String url = Manager.url + "/images/avatar/" + avatar;
        imageViewUserAvatar.setImage(new Image(url));
    }

    private boolean uploadAvatar(File file) throws Exception{
        boolean status = false;

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Response response;
        try {
            response = Manager.userApiService.uploadUserAvatar(body, Manager.token).execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 200){
            status = true;
            this.userAvatar = response.body().toString();
            System.out.println(this.userAvatar);

        }else{
            throw new Exception(response.errorBody().string());
        }

        return status;
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
