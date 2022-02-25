package com.example.mainapp;

import Models.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField passfield;
    @FXML
    private TextField unamefield;

    final private LoginModel loginModel;

    public LoginController() {
        loginModel = new LoginModel();
    }

    @FXML
    public void signMeUpAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Customer mode");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void signInAction(ActionEvent event) {
        // username is case-insensitive
        if(loginModel.validLogin(unamefield.getText().toLowerCase(),passfield.getText()) == 1) {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cars-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Rent a car");
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Invalid login");
            errorLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    void LoginAdmin(MouseEvent event) {
        if(loginModel.validAdmin(unamefield.getText().toLowerCase(),passfield.getText()) == 1) {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Rent a car");
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Invalid login");
            errorLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    void LoginSecretary(MouseEvent event) {
        if(loginModel.validSecretary(unamefield.getText().toLowerCase(),passfield.getText()) == 1) {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Rent a car");
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Invalid login");
            errorLabel.setTextFill(Color.RED);
        }
    }

}
