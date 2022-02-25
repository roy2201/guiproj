package com.example.mainapp;

import Models.AddRemoveCarModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddRemoveCarController {

    private AddRemoveCarModel addRemoveCarModel = new AddRemoveCarModel();

    @FXML
    private Button btnadd;
    @FXML
    private Button btnback;
    @FXML
    private Button btnremove;
    @FXML
    private Label labeladd;
    @FXML
    private Label labelremove;
    @FXML
    private TextField txtbranch;
    @FXML
    private TextField txtcarid;
    @FXML
    private TextField txtcolor;
    @FXML
    private TextField txtmodel;
    @FXML
    private TextField txtprice;
    @FXML
    private TextField txttype;
    @FXML
    private TextField txtyear;

    public void AddCarClicked(ActionEvent event){
        if(txtbranch.getText().isEmpty() || txtcolor.getText().isEmpty() || txtyear.getText().isEmpty() || txtmodel.getText().isEmpty() || txtprice.getText().isEmpty() || txttype.getText().isEmpty()){
            labeladd.setText("Missing info");
        }
        else {
            addRemoveCarModel.AddCar(txtbranch.getText(), txtcolor.getText(), txtyear.getText(), txtmodel.getText(), txtprice.getText(), txttype.getText());
            labeladd.setText("Car is added successfully");
        }
        labelremove.setText("");
        txtbranch.setText("");
        txtcolor.setText("");
        txtyear.setText("");
        txtmodel.setText("");
        txtprice.setText("");
        txttype.setText("");
    }

    public void RemoveCarClicked(ActionEvent event){

        int errorcode;
        String msg = "";

        if(txtcarid.getText().isEmpty()){
            labelremove.setText("Missing info");
        }
        else {
            errorcode = addRemoveCarModel.RemoveCar(txtcarid.getText());
            if (errorcode == 0) {
                msg = "No car with such ID to be removed";
            }
            else if (errorcode == 1) {
                msg = "The car is removed successfully";
            }
            labelremove.setText(msg);
            txtcarid.setText("");
        }
       labeladd.setText("");
    }

    public void BackClicked(MouseEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Car Mode");
        stage.setScene(scene);
        stage.show();
    }
}
