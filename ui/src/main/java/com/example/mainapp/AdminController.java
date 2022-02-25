package com.example.mainapp;

import Models.Driver;
import Models.Employee;
import Models.LoginModel;
import Models.ManagerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    Employee e;
    private int adding;
    private int removing;
    public int viewing;
    private ManagerModel managerModel = new ManagerModel();
    /*
            adding: 1:admin, 2:secretary, 3:driver
     */
    @FXML
    private Button btnremove;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnview;
    @FXML
    private ToggleGroup employees;
    @FXML
    private Label labelinfo;
    @FXML
    private RadioButton rbtnadmin;
    @FXML
    private RadioButton rbtndriver;
    @FXML
    private RadioButton rbtnsecretary;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;
    @FXML
    private TableView tableInfo;
    @FXML
    private Button btnGoToAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(LoginModel.admin_secreatry == 2){
            rbtnadmin.setDisable(true);
        }
    }

    public void logOutAction(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Rent a car");
        stage.setScene(scene);
        stage.show();
    }

    public void AddEmployeeClicked (){
        int errorcode;
        String msg = "";

        if(rbtnadmin.isSelected()) {
            adding = 1;
             e = new ManagerModel(txtusername.getText(),txtpassword.getText());
        }
         if(rbtnsecretary.isSelected()) {
            adding = 2;
             e = new ManagerModel(txtusername.getText(),txtpassword.getText());
        }
        if(rbtndriver.isSelected()) {
            adding = 3;
            e = new Driver(txtusername.getText(),txtpassword.getText());
        }

        errorcode = managerModel.AddEmployee(e,LoginModel.admin_secreatry,adding);

        if(LoginModel.admin_secreatry == 1 && adding == 1){
            if(errorcode == 1)
                msg = "The new admin is added successfuly";
            else if(errorcode == -1)
                msg = "No admin with such ID";
        }

        else if(LoginModel.admin_secreatry == 1 && adding == 2){
            if(errorcode ==1)
                msg = "The new secretary is added successfuly";
            else if(errorcode == -1)
                msg = "No admin with such ID";
        }

        else if (LoginModel.admin_secreatry == 1 && adding == 3){
            if(errorcode == 1)
                msg = "The new driver is added successfuly";
            else if(errorcode == -1)
                msg = "No admin with such ID";
        }

        else if(LoginModel.admin_secreatry == 2 && adding == 2){
            if(errorcode == 1)
                msg = "The new secretary is added successfuly";
            else if(errorcode == -1)
                msg = "No secretary with such ID";
        }

        else if(LoginModel.admin_secreatry == 2 && adding == 3){
            if(errorcode == 1)
                msg = "The new driver is added successfuly";
            else if(errorcode == -1)
                msg = "No secretary with such ID";
        }

        labelinfo.setText(msg);

    }

    public void RemovedEmployeeClicked(ActionEvent event){

        int errorcode;
        String msg = "";

        if(rbtnadmin.isSelected()) {
            removing = 1;
        }
        if(rbtnsecretary.isSelected()) {
            removing = 2;
        }
        if(rbtndriver.isSelected()) {
            removing = 3;
        }

        errorcode = managerModel.RemoveEmployee(txtid.getText(),LoginModel.admin_secreatry,removing);

        if(LoginModel.admin_secreatry == 1 && removing == 1){
            if(errorcode == -2)
                msg = "Cant remove admin, this admin is refered to anaother manager";
            else if(errorcode == -1)
                msg = "No admin with such ID";
            else if(errorcode == 0)
                msg = "No admin with such ID to be removed";
            else if(errorcode == 1)
                msg = "The admin is removed successfully";
        }

        if(LoginModel.admin_secreatry == 1 && removing == 2){
            if(errorcode == -2)
                msg = "Cant remove secretary, this secretary is refered to anaother manager";
            else if(errorcode == -1)
                msg = "No admin with such ID";
            else if(errorcode == 0)
                msg = "No secretary with such ID to be removed";
            else if(errorcode == 1)
                msg = "The secretary is removed successfully";
        }

        if(LoginModel.admin_secreatry == 1 && removing == 3){
            if(errorcode == -2)
                msg = "Cant remove driver, this driver is refered to anaother manager";
            else if(errorcode == -1)
                msg = "No admin with such ID";
            else if(errorcode == 0)
                msg = "No driver with such ID to be removed";
            else if(errorcode == 1)
                msg = "The driver is removed successfully";
        }

        if(LoginModel.admin_secreatry == 2 && removing == 2){
            if(errorcode == -2)
                msg = "Cant remove secretary, this secretary is refered to anaother manager";
            else if(errorcode == -1)
                msg = "No secretary with such ID";
            else if(errorcode == 0)
                msg = "No secretary with such ID to be removed";
            else if(errorcode == 1)
                msg = "The secreatry is removed successfully";
        }

        if(LoginModel.admin_secreatry == 2 && removing == 3){
            if(errorcode == -2)
                msg = "Cant remove driver, this driver is refered to anaother manager";
            else if(errorcode == -1)
                msg = "No secretary with such ID";
            else if(errorcode == 0)
                msg = "No driver with such ID to be removed";
            else if(errorcode == 1)
                msg = "The driver is removed successfully";
        }

        labelinfo.setText(msg);
    }

    public void ViewEmployeeClicked(ActionEvent event){

        tableInfo.getColumns().clear();

        if(rbtnadmin.isSelected()) {
            viewing = 1;
        }
        if(rbtnsecretary.isSelected()) {
            viewing = 2;
        }
        if(rbtndriver.isSelected()) {
            viewing = 3;
        }

        managerModel.ViewEmployee(tableInfo,LoginModel.admin_secreatry,viewing);
    }

    public void GoToCarMode(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddRemoveCar.fxml"));
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