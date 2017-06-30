package controller;

import com.sun.xml.internal.txw2.TXW;
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
import model.converters.UserDTOConverter;
import model.dto.ItemDTO;
import model.dto.UserDTO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ItemController implements Initializable {

    private ObservableList<UserDTO> usersList;

    private Stage window;
    private ItemDTO item;

    private String imageName = "default.png";

    @FXML private ImageView imageViewItemImage;
    @FXML private Button buttonBrowseImage;
    @FXML private TextField textFieldItemID;
    @FXML private TextField textFieldItemName;
    @FXML private TextArea textAreaItemDesc;
    @FXML private ComboBox comboBoxItemUser;
    @FXML private Button buttonItemSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadUsers();

        comboBoxItemUser.setConverter(new UserDTOConverter());
        comboBoxItemUser.getItems().addAll(usersList);

        if(item != null){

            this.imageName = item.getPicture();
            loadImage(item.getPicture());

            textFieldItemID.setText(String.valueOf(item.getId()));
            textFieldItemName.setText(item.getName());
            textAreaItemDesc.setText(item.getDescription());

            for(UserDTO u : usersList){
                if(u.getId() == item.getUserId()){
                    comboBoxItemUser.getSelectionModel().select(u);
                }
            }

        }else{
            loadImage(this.imageName);
            buttonItemSave.setText("Add");
        }

    }

    private void loadUsers(){

        Response response = null;
        try{
            response = Manager.userApiService
                    .getAllUsers(Manager.token)
                    .execute();
        }catch (Exception ex){
            return;
        }

        if(response.code() == 200){

            List<UserDTO> users = (List<UserDTO>) response.body();
            if(users != null) usersList = FXCollections.observableList(users);
        }

    }

    private void loadImage(String imageName){
        String url = Manager.url + "/images/items/" + imageName;
        imageViewItemImage.setImage(new Image(url));
    }

    public void choseImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose image");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg","*.png"));

        File file = fileChooser.showOpenDialog(window);

        if(file != null) {
            System.out.println(file.getName());

            boolean status;
            try {
                status = uploadImage(file);
            }catch (Exception ex){
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                return;
            }

            if(status){
                loadImage(this.imageName);
                new Alert(Alert.AlertType.INFORMATION, "Upload successful!").showAndWait();
            }else{
                new Alert(Alert.AlertType.ERROR, "Upload unsuccessful!").showAndWait();
            }
        }


    }


    public void onButtonItemSave(){

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(textFieldItemName.getText());
        itemDTO.setDescription(textAreaItemDesc.getText());
        UserDTO selectedUser = (UserDTO) comboBoxItemUser.getValue();
        itemDTO.setUserId(selectedUser.getId());
        itemDTO.setPicture(this.imageName);

        try {
            if (item == null) {
                // send create request
                sendCreateRequest(itemDTO);
            } else {
                // send edit request
                itemDTO.setId(item.getId());
                sendEditRequest(itemDTO);
            }
        }catch (Exception ex){
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            return;
        }

        window.close();

    }

    private boolean sendCreateRequest(ItemDTO itemDTO) throws Exception{
        boolean status = false;

        Response response;
        try{
            response = Manager.itemApiService
                    .addItem(itemDTO, Manager.token)
                    .execute();
        }catch (Exception ex){
            throw ex;
        }

        if(response.code() == 201){
            status = true;
        }else {
            throw new Exception(response.errorBody().string());
        }

        return status;
    }

    private boolean sendEditRequest(ItemDTO itemDTO) throws Exception{
        boolean status = false;

        Response response;
        try{
            response = Manager.itemApiService
                    .editItem(itemDTO, itemDTO.getId(), Manager.token)
                    .execute();
        }catch (Exception ex){
            throw ex;
        }

        if(response.code() == 200){
            status = true;
        }else{
            throw new Exception(response.errorBody().string());
        }

        return status;
    }

    private boolean uploadImage(File file) throws Exception{
        boolean status = false;

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Response response;
        try {
            response = Manager.userApiService.uploadItemPicture(body, Manager.token).execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 200){
            status = true;
            this.imageName = response.body().toString();
            System.out.println(this.imageName);

        }else{
            throw new Exception(response.errorBody().string());
        }

        return status;
    }

    public void setItem(ItemDTO item){
        this.item = item;
    }

    public void setWindow(Stage window){
        this.window = window;
    }

}
