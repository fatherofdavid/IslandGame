package com.annapolisWorks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    Button button1 = new Button();

    public void doMyAction(ActionEvent event){
        System.out.println("I've been clicked!");
    }

    public void subtractAction(int i){
        //update number of remaining actions
        //gray out action buttons if no actions remaining
    }
}
