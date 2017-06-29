package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dto.ItemDTO;

import java.net.URL;
import java.util.ResourceBundle;


public class ItemController implements Initializable {

    private Stage window;
    private ItemDTO item;

    @FXML private Button buttonItemSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(item == null){
            buttonItemSave.setText("Add");

        }else{
            window.setTitle("Item Edit");

        }

    }


    public void setItem(ItemDTO item){
        this.item = item;
    }

    public void setWindow(Stage window){
        this.window = window;
    }

}
