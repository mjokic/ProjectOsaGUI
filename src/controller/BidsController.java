package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Auction;
import model.Bid;
import model.Manager;
import model.User;
import model.converters.AuctionConverter;
import model.converters.UserDTOConverter;
import model.dto.AuctionDTO;
import model.dto.BidDTO;
import model.dto.UserDTO;
import retrofit2.Response;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class BidsController implements Initializable {

    private Stage window;

    private BidDTO bid;
    private ObservableList<UserDTO> usersList;
    private ObservableList<Auction> auctionsList;


    @FXML private TextField textFieldBidID;
    @FXML private TextField textFieldPrice;
    @FXML private DatePicker datePickerBidDate;
    @FXML private ComboBox comboBoxUser;
    @FXML private ComboBox comboBoxAuction;
    @FXML private Button buttonBidSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadUsers();
        loadAuctions();

        comboBoxUser.setConverter(new UserDTOConverter());
        comboBoxUser.getItems().addAll(usersList);

        comboBoxAuction.setConverter(new AuctionConverter());
        comboBoxAuction.getItems().addAll(auctionsList);

        if(this.bid != null){
            textFieldBidID.setText(String.valueOf(bid.getId()));
            textFieldPrice.setText(String.valueOf(bid.getPrice()));

        }else {
            buttonBidSave.setText("Add");
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

    private void loadAuctions(){
        Response response;

        try{
            response = Manager.auctionApiService
                    .getAllAuctions(Manager.token)
                    .execute();
        }catch (Exception ex){
            return;
        }

        if(response.code() == 200){
            List<Auction> auctions = (List<Auction>) response.body();
            auctionsList = FXCollections.observableList(auctions);
        }

    }

    public void onSaveButtonClick(){

        boolean status;

        try {
            status = sendCreateBidRequest();
        }catch (Exception ex){
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            return;
        }


        if(status){
            window.close();
        }else{
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").showAndWait();
        }

    }

    private boolean sendCreateBidRequest() throws Exception{
        boolean status = false;

        int bidStartPrice = Integer.parseInt(textFieldPrice.getText());
        Date date = new SimpleDateFormat("dd/mm/yyyy").parse(datePickerBidDate.getEditor().getText());
        Auction auction = (Auction) comboBoxAuction.getValue();
        UserDTO userDTO = (UserDTO) comboBoxUser.getValue();

        BidDTO bidDTO = new BidDTO(bidStartPrice, date, auction.getId(), userDTO.getId());

        Response response;

        try {
            response = Manager.bidApiService
                    .createBid(bidDTO, Manager.token)
                    .execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 201){
            status = true;
        }else{
            throw new Exception(response.errorBody().string());
        }

        return status;
    }

    public void setWindow(Stage window){
        this.window = window;
    }

    private void setBid(BidDTO bid){
        this.bid = bid;
    }
}
