package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class BidsController implements Initializable {

    private Stage window;

    @FXML private TextField textFieldBidID;
    @FXML private TextField textFieldPrice;
    @FXML private Button buttonBidSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setWindow(Stage window){
        this.window = window;
    }

}
