package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Auction;
import model.Manager;
import model.converters.ItemConverter;
import model.converters.UserDTOConverter;
import model.dto.AuctionDTO;
import model.dto.ItemDTO;
import model.dto.UserDTO;
import retrofit2.Response;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

    private Auction auction;
    private Stage window;

    private ObservableList<UserDTO> usersList;
    private ObservableList<ItemDTO> itemsList;

    @FXML private TextField textFieldAuctionID;
    @FXML private TextField textFieldAuctionPrice;
    @FXML private DateTimePicker datePickerAuctionStartDate;
//    @FXML private DatePicker datePickerAuctionEndDate;
    @FXML private DateTimePicker datePickerAuctionEndDate;
    @FXML private ComboBox comboBoxAuctionUsers;
    @FXML private ComboBox comboBoxAuctionItems;
    @FXML private Button buttonAuctionAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadItems();
        loadUsers();

        comboBoxAuctionUsers.setConverter(new UserDTOConverter());
        comboBoxAuctionUsers.getItems().addAll(usersList);

        comboBoxAuctionItems.setConverter(new ItemConverter());
        comboBoxAuctionItems.getItems().addAll(itemsList);

        if(auction != null){
            textFieldAuctionID.setText(String.valueOf(auction.getId()));
            textFieldAuctionPrice.setText(String.valueOf(auction.getStartPrice()));

            String startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(auction.getStartDate());
            datePickerAuctionStartDate.getEditor().setText(startDate);

            String endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(auction.getEndDate());
            datePickerAuctionEndDate.getEditor().setText(endDate);

            for(UserDTO u : usersList){
                if(u.getId() == auction.getUser().getId())
                    comboBoxAuctionUsers.getSelectionModel().select(u);
            }

            for(ItemDTO i : itemsList){
                if(i.getId() == auction.getItem().getId()){
                    comboBoxAuctionItems.getSelectionModel().select(i);
                }
            }

//            window.setTitle("Edit Auction");
            buttonAuctionAdd.setText("Save");
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

    private void loadItems(){
        Response response;
        try{
            response = Manager.itemApiService
                    .getAllItems(Manager.token)
                    .execute();
        }catch (Exception ex){
            return;
        }

        if(response.code() == 200){

            List<ItemDTO> items = (List<ItemDTO>) response.body();
            if(items != null) itemsList = FXCollections.observableList(items);
        }
    }


    public void onButtonAuctionAddClick(){

        boolean status;

        try {
            if(auction == null){
                status = sendAuctionAddRequest();
            }else{
                status = sendAuctionEditRequest();
            }

        }catch (Exception ex){
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            return;
        }

        if(!status){
            new Alert(Alert.AlertType.ERROR, "Something went wrong!")
                    .showAndWait();
            return;
        }

        window.close();
    }

    private boolean sendAuctionAddRequest() throws Exception{
        boolean status = false;

        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setStartPrice(Double.parseDouble(textFieldAuctionPrice.getText()));

        Date startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .parse(datePickerAuctionStartDate.getEditor().getText());
        auctionDTO.setStartDate(startDate);

        Date endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .parse(datePickerAuctionEndDate.getEditor().getText());
        auctionDTO.setEndDate(endDate);

        UserDTO selectedUser = (UserDTO) comboBoxAuctionUsers.getValue();
        auctionDTO.setUser_id(selectedUser.getId());

        ItemDTO selectedItem = (ItemDTO) comboBoxAuctionItems.getValue();
        auctionDTO.setItem_id(selectedItem.getId());


        Response response;
        try {
            response = Manager.auctionApiService
                    .createAuction(auctionDTO, Manager.token)
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

    private boolean sendAuctionEditRequest() throws Exception{
        boolean status = false;

        AuctionDTO auctionDTO = new AuctionDTO(auction);

        auctionDTO.setStartPrice(Double.parseDouble(textFieldAuctionPrice.getText()));

        Date startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .parse(datePickerAuctionStartDate.getEditor().getText());
        auctionDTO.setStartDate(startDate);

        Date endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .parse(datePickerAuctionEndDate.getEditor().getText());
        auctionDTO.setEndDate(endDate);

        UserDTO selectedUser = (UserDTO) comboBoxAuctionUsers.getValue();
        auctionDTO.setUser_id(selectedUser.getId());

        ItemDTO selectedItem = (ItemDTO) comboBoxAuctionItems.getValue();
        auctionDTO.setItem_id(selectedItem.getId());

        Response response;
        try {
            response = Manager.auctionApiService
                    .editAuction(auctionDTO, auctionDTO.getId(), Manager.token)
                    .execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 200){
            status = true;
        }else{
            throw new Exception(response.errorBody().string());
        }

        return status;
    }


    public void setAuction(Auction auction){
        this.auction = auction;
    }

    public void setWindow(Stage window){
        this.window = window;
    }
}
