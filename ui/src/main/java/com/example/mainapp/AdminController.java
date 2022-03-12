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
import java.util.concurrent.atomic.AtomicReference;

/**CHECKED**/
public class AdminController implements Initializable {

    private Employee e;
    private int adding;
    private int removing;
    private int viewing;
    final private ManagerModel managerModel = new ManagerModel();

    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnView;
    @FXML
    private ToggleGroup employees;
    @FXML
    private Label labelInfo;
    @FXML
    private RadioButton radioAdminBtn;
    @FXML
    private RadioButton radioDriverBtn;
    @FXML
    private RadioButton radioSecretaryBtn;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TableView<?> tableInfo;
    @FXML
    private Button btnGoToAdd;

    //disable remove admin radio button if secretary logged in
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LoginModel.admin_secreatry == 2) {
            radioAdminBtn.setDisable(true);
        }
    }

    //add new employee, take parent into consideration
    public void AddEmployeeClicked() {
        int errorCode;
        String msg = "";
        if (radioAdminBtn.isSelected()) {
            adding = 1;
            e = new ManagerModel(txtUserName.getText(), txtPassword.getText());
        }
        if (radioSecretaryBtn.isSelected()) {
            adding = 2;
            e = new ManagerModel(txtUserName.getText(), txtPassword.getText());
        }
        if (radioDriverBtn.isSelected()) {
            adding = 3;
            e = new Driver(txtUserName.getText(), txtPassword.getText());
        }
        errorCode = managerModel.AddEmployee(e, LoginModel.admin_secreatry, adding);
        if (LoginModel.admin_secreatry == 1 && adding == 1) {
            if (errorCode == 1) msg = "The new admin is added successfully";
            else if (errorCode == -1) msg = "No admin with such ID";
        } else if (LoginModel.admin_secreatry == 1 && adding == 2) {
            if (errorCode == 1) msg = "The new secretary is added successfully";
            else if (errorCode == -1) msg = "No admin with such ID";
        } else if (LoginModel.admin_secreatry == 1 && adding == 3) {
            if (errorCode == 1) msg = "The new driver is added successfully";
            else if (errorCode == -1) msg = "No admin with such ID";
        } else if (LoginModel.admin_secreatry == 2 && adding == 2) {
            if (errorCode == 1) msg = "The new secretary is added successfully";
            else if (errorCode == -1) msg = "No secretary with such ID";
        } else if (LoginModel.admin_secreatry == 2 && adding == 3) {
            if (errorCode == 1) msg = "The new driver is added successfully";
            else if (errorCode == -1) msg = "No secretary with such ID";
        }
        labelInfo.setText(msg);
    }

    //remove employee; secretary or admin
    public void RemovedEmployeeClicked() {
        int errorCode;
        AtomicReference<String> msg = new AtomicReference<>("");
        if (radioAdminBtn.isSelected()) {
            removing = 1;
        }
        if (radioSecretaryBtn.isSelected()) {
            removing = 2;
        }
        if (radioDriverBtn.isSelected()) {
            removing = 3;
        }
        errorCode = managerModel.RemoveEmployee(txtId.getText(), LoginModel.admin_secreatry, removing);
        if (LoginModel.admin_secreatry == 1 && removing == 1) {
            if (errorCode == -2) msg.set("Cant remove admin, this admin is referred to another manager");
            else if (errorCode == -1) msg.set("No admin with such ID");
            else if (errorCode == 0) msg.set("No admin with such ID to be removed");
            else if (errorCode == 1) msg.set("The admin is removed successfully");
        }
        if (LoginModel.admin_secreatry == 1 && removing == 2) {
            if (errorCode == -2) msg.set("Cant remove secretary, this secretary is referred to another manager");
            else if (errorCode == -1) msg.set("No admin with such ID");
            else if (errorCode == 0) msg.set("No secretary with such ID to be removed");
            else if (errorCode == 1) msg.set("The secretary is removed successfully");
        }
        if (LoginModel.admin_secreatry == 1 && removing == 3) {
            if (errorCode == -2) msg.set("Cant remove driver, this driver is referred to another manager");
            else if (errorCode == -1) msg.set("No admin with such ID");
            else if (errorCode == 0) msg.set("No driver with such ID to be removed");
            else if (errorCode == 1) msg.set("The driver is removed successfully");
        }
        if (LoginModel.admin_secreatry == 2 && removing == 2) {
            if (errorCode == -2) msg.set("Cant remove secretary, this secretary is referred to another manager");
            else if (errorCode == -1) msg.set("No secretary with such ID");
            else if (errorCode == 0) msg.set("No secretary with such ID to be removed");
            else if (errorCode == 1) msg.set("The secretary is removed successfully");
        }
        if (LoginModel.admin_secreatry == 2 && removing == 3) {
            if (errorCode == -2) msg.set("Cant remove driver, this driver is referred to another manager");
            else if (errorCode == -1) msg.set("No secretary with such ID");
            else if (errorCode == 0) msg.set("No driver with such ID to be removed");
            else if (errorCode == 1) msg.set("The driver is removed successfully");
        }
        labelInfo.setText(msg.get());
    }

    //view all added employees for specific manager or secretary
    public void ViewEmployeeClicked() {
        tableInfo.getColumns().clear();
        if (radioAdminBtn.isSelected()) {
            viewing = 1;
        }
        if (radioSecretaryBtn.isSelected()) {
            viewing = 2;
        }
        if (radioDriverBtn.isSelected()) {
            viewing = 3;
        }
        managerModel.ViewEmployee(tableInfo, LoginModel.admin_secreatry, viewing);
    }

    //nav functions
    public void logOutAction(MouseEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
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

    public void GoToCarMode(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
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
    //end nav

}