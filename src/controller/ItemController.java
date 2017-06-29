package controller;

import com.sun.xml.internal.txw2.TXW;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Manager;
import model.converters.UserDTOConverter;
import model.dto.ItemDTO;
import model.dto.UserDTO;
import retrofit2.Response;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ItemController implements Initializable {

    private ObservableList<UserDTO> usersList;

    private Stage window;
    private ItemDTO item;

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
//            window.setTitle("Item Edit");
            textFieldItemID.setText(String.valueOf(item.getId()));
            textFieldItemName.setText(item.getName());
            textAreaItemDesc.setText(item.getDescription());

            for(UserDTO u : usersList){
                if(u.getId() == item.getUserId()){
                    comboBoxItemUser.getSelectionModel().select(u);
                }
            }

        }else{
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

    public void onButtonItemSave(){

        try {
            if (item == null) {
                // send create request
                sendCreateRequest();
            } else {
                // send edit request
                sendEditRequest();
            }
        }catch (Exception ex){
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            return;
        }

        window.close();

    }

    private boolean sendCreateRequest() throws Exception{
        boolean status = false;

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(textFieldItemName.getText());
        itemDTO.setDescription(textAreaItemDesc.getText());
        UserDTO selectedUser = (UserDTO) comboBoxItemUser.getValue();
        itemDTO.setUserId(selectedUser.getId());

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

    private boolean sendEditRequest() throws Exception{
        boolean status = false;

        item.setName(textFieldItemName.getText());
        item.setDescription(textAreaItemDesc.getText());
        UserDTO selectedUser = (UserDTO)comboBoxItemUser.getValue();
        item.setUserId(selectedUser.getId());

        Response response;
        try{
            response = Manager.itemApiService
                    .editItem(item, item.getId(), Manager.token)
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


    public void setItem(ItemDTO item){
        this.item = item;
    }

    public void setWindow(Stage window){
        this.window = window;
    }

}
