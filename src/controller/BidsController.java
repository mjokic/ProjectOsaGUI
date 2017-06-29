package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Manager;
import model.converters.UserConverter;
import model.dto.BidDTO;
import model.dto.UserDTO;
import retrofit2.Response;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class BidsController implements Initializable {

    private Stage window;

    private BidDTO bid;
    private ObservableList<UserDTO> usersList;


    @FXML private TextField textFieldBidID;
    @FXML private TextField textFieldPrice;
    @FXML private ComboBox comboBoxUser;
    @FXML private Button buttonBidSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadUsers();
//        comboBoxUser.setItems(usersList);


        comboBoxUser.setConverter(new UserConverter());
        comboBoxUser.getItems().addAll(usersList);


        if(this.bid != null){
            textFieldBidID.setText(String.valueOf(bid.getId()));
            textFieldPrice.setText(String.valueOf(bid.getPrice()));

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


    public void onSaveButtonClick(){

        UserDTO userDTO = (UserDTO) comboBoxUser.getValue();
        System.out.println(userDTO);

    }

    public void setWindow(Stage window){
        this.window = window;
    }

    private void setBid(BidDTO bid){
        this.bid = bid;
    }
}
