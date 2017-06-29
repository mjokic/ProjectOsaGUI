package controller;


import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Auction;
import model.Manager;
import model.dto.BidDTO;
import model.dto.ItemDTO;
import model.dto.UserDTO;
import retrofit2.Response;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage window;

    @FXML
    private TabPane tabPane;

    @FXML private Tab tabAuctions;
    @FXML private Tab tabItems;
    @FXML private Tab tabBids;
    @FXML private Tab tabUsers;

    // auctions tab
    @FXML private TableView tableViewAuctions;
    @FXML private TableColumn tableColumnAuctionId;
    @FXML private TableColumn tableColumnStartPrice;
    @FXML private TableColumn tableColumnstartDate;
    @FXML private TableColumn tableColumnendDate;
    @FXML private TableColumn tableColumnAuctionUser;
    @FXML private TableColumn tableColumnAuctionItem;
    @FXML private TableColumn tableColumnAuctionBids;
    @FXML private TableColumn tableColumnAuctionOver;
    //

    // items tab
    @FXML private TableView tableViewItems;
    @FXML private TableColumn tableColumnItemsId;
    @FXML private TableColumn tableColumnItemsName;
    @FXML private TableColumn tableColumnItemsDesc;
    @FXML private TableColumn tableColumnItemsSold;
    //

    // bids tab
    @FXML private TableView tableViewBids;
    @FXML private TableColumn tableColumnBidsId;
    @FXML private TableColumn tableColumnBidsPrice;
    @FXML private TableColumn tableColumnBidsDate;
    @FXML private TableColumn tableColumnBidsUser;
    @FXML private TableColumn tableColumnBidsAuction;
    //

    // users tab
    @FXML
    private TableView tableViewUsers;
    @FXML
    private TableColumn tableColumnUserId;
    @FXML
    private TableColumn tableColumnEmail;
    @FXML
    private TableColumn tableColumnName;
    @FXML
    private TableColumn tableColumnAddress;
    @FXML
    private TableColumn tableColumnPhone;
    @FXML
    private TableColumn tableColumnRole;
    @FXML
    private TableColumn tableColumnActive;
    //


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabAuctions.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(tabAuctions.isSelected()){
                    System.out.println("Auctions Selected!");
                    try {
                        loadAuctions();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        tabItems.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(tabItems.isSelected()) {
                    System.out.println("Items Selected!");
                    try {
                        loadItems();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        tabBids.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Bids Selected!");
                try {
                    loadBids();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        tabUsers.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Users Selected!");
                try {
                    loadUsers();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        tableColumnUserId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory("address"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        tableColumnRole.setCellValueFactory(new PropertyValueFactory("role"));
        tableColumnActive.setCellValueFactory(new PropertyValueFactory("active"));

        tableViewUsers.setRowFactory(param ->{
                TableRow<UserDTO> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2){
                            // open new window with user info...
                            UserDTO user = row.getItem();
                            System.out.println(user);

                            try {
                                URL path = getClass().getClassLoader().getResource("view/userWindow.fxml");
                                FXMLLoader loader = new FXMLLoader(path);

                                loader.setControllerFactory(new Callback<Class<?>, Object>() {
                                    @Override
                                    public Object call(Class<?> param) {

                                        UserController userController = new UserController();
                                        userController.setUser(user);
                                        return userController;

                                    }
                                });

                                Parent layout = loader.load();

                                Scene scene = new Scene(layout, 300, 400);
                                Stage window1 = new Stage();
                                window1.initModality(Modality.APPLICATION_MODAL);
                                window1.initOwner(window);
                                window1.setTitle("User Edit");
                                window1.setScene(scene);

                                UserController uC = loader.getController();
                                uC.setWindow(window1);

                                window1.showAndWait();

                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                        }
                    }
                });
                return row;
                }
        );


        tableColumnAuctionId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnStartPrice.setCellValueFactory(new PropertyValueFactory("startPrice"));
        tableColumnstartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
        tableColumnendDate.setCellValueFactory(new PropertyValueFactory("endDate"));
        tableColumnAuctionUser.setCellValueFactory(new PropertyValueFactory("UserId"));
        tableColumnAuctionItem.setCellValueFactory(new PropertyValueFactory("ItemId"));
        tableColumnAuctionBids.setCellValueFactory(new PropertyValueFactory("BidsCount"));
        tableColumnAuctionOver.setCellValueFactory(new PropertyValueFactory("over"));


        tableColumnItemsId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnItemsName.setCellValueFactory(new PropertyValueFactory("name"));
        tableColumnItemsDesc.setCellValueFactory(new PropertyValueFactory("description"));
        tableColumnItemsSold.setCellValueFactory(new PropertyValueFactory("sold"));


        tableColumnBidsId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnBidsPrice.setCellValueFactory(new PropertyValueFactory("price"));
        tableColumnBidsDate.setCellValueFactory(new PropertyValueFactory("dateTime"));
        tableColumnBidsUser.setCellValueFactory(new PropertyValueFactory("user_id"));
        tableColumnBidsAuction.setCellValueFactory(new PropertyValueFactory("auction_id"));

        tableViewBids.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    BidDTO bidDTO = (BidDTO)tableViewBids.getSelectionModel().getSelectedItem();
                    openBidWindow(bidDTO);
                }
            }
        });

//        System.out.println("Token " + Manager.token);
    }


    private void loadAuctions() throws Exception {
        Response response = Manager.auctionApiService
                .getAllAuctions(Manager.token)
                .execute();

        List<Auction> auctions = (List<Auction>) response.body();

        Manager.auctions = FXCollections.observableArrayList();
        for(Auction a : auctions){
            Manager.auctions.add(a);
        }

        tableViewAuctions.setItems(Manager.auctions);

    }

    private void loadItems() throws Exception {
        Response response = Manager.itemApiService
                .getAllItems(Manager.token)
                .execute();

        List<ItemDTO> items = (List<ItemDTO>) response.body();

        Manager.items = FXCollections.observableArrayList();
        for(ItemDTO i : items){
            Manager.items.add(i);
        }

        tableViewItems.setItems(Manager.items);

    }

    private void loadBids() throws Exception {
        Response response = Manager.bidApiService
                .getAllBids(Manager.token)
                .execute();

        List<BidDTO> bids = (List<BidDTO>) response.body();

        Manager.bids = FXCollections.observableArrayList();
        for(BidDTO b : bids){
            Manager.bids.add(b);
        }

        tableViewBids.setItems(Manager.bids);

    }

    private void loadUsers() throws Exception{

        Response response = Manager.userApiService
                .getAllUsers(Manager.token)
                .execute();

        List<UserDTO> users = (List<UserDTO>) response.body();

        Manager.users = FXCollections.observableArrayList();
        for(UserDTO u : users){
            Manager.users.add(u);
        }

        tableViewUsers.setItems(Manager.users);

    }

    public void setWindow(Stage window){
        this.window = window;
    }



    public void addBid(){
        openBidWindow(null);
    }

    public void deleteBid(){
        int index = tableViewBids.getSelectionModel().getSelectedIndex();
        if(index == -1){
            new Alert(Alert.AlertType.ERROR, "Select bid to delete!").showAndWait();
            return;
        }

        BidDTO bidDTO = (BidDTO) tableViewBids.getItems().get(index);
        if(bidDTO != null){
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", yes, no);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == yes){
                boolean status = sendBidDeleteRequest(bidDTO.getId());
                if(!status){
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!");
                }
            }
        }

    }

    private void openBidWindow(BidDTO bidDTO){
        try{
            URL path = getClass().getClassLoader().getResource("view/bidWindow.fxml");
            FXMLLoader loader = new FXMLLoader(path);

            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    BidsController bidsController = new BidsController();
                    bidsController.setBid(bidDTO);
                    return bidsController;
                }
            });

            Parent layout = loader.load();
            Scene scene = new Scene(layout, 300, 400);
            Stage window1 = new Stage();
            window1.initModality(Modality.APPLICATION_MODAL);
            window1.initOwner(window);
            window1.setTitle("Bid Add");
            window1.setScene(scene);


            BidsController bidsController = loader.getController();
            bidsController.setWindow(window1);

            window1.showAndWait();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void addUser() throws Exception{

        URL path = getClass().getClassLoader().getResource("view/userWindow.fxml");
        FXMLLoader loader = new FXMLLoader(path);
        Parent layout = loader.load();

        Scene scene = new Scene(layout, 300, 400);
        Stage window1 = new Stage();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.initOwner(window);
        window1.setTitle("User Add");
        window1.setScene(scene);

        UserController uC = loader.getController();
        uC.setWindow(window1);

        window1.showAndWait();

    }

    public void deleteUser() throws Exception{

        int index = tableViewUsers.getSelectionModel().getSelectedIndex();
        if(index == -1){
            new Alert(Alert.AlertType.ERROR, "Select user to delete!").showAndWait();
            return;
        }

        UserDTO user = (UserDTO) tableViewUsers.getItems().get(index);
        if(user != null){
            // delete user
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", yes, no);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == yes){
                boolean status = sendUserDeleteRequest(user.getId());
                if(!status){
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!");
                    return;
                }
            }
        }

    }



    private boolean sendUserDeleteRequest(long id){
        boolean status = false;

        Response response;

        try {
            response = Manager.userApiService.deleteUser(id, Manager.token).execute();
        }catch (Exception ex){
            return status;
        }

        if(response.code() == 200){
            return true;
        }

        return status;
    }

    private boolean sendBidDeleteRequest(long id){
        boolean status = false;
        Response response;
        try{
            response = Manager.bidApiService
                    .deleteBid(id, Manager.token)
                    .execute();
        }catch (Exception ex){
            return status;
        }


        if(response.code() == 200){
            status = true;
        }

        return status;

    }
}
