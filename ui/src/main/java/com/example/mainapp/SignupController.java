package com.example.mainapp;

import Models.SignupModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    SignupModel signUpModel;

    @FXML
    private TextField cpasswordfield;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField passwordfield;
    @FXML
    private TextField unamefield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpModel = new SignupModel();
    }

    @FXML
    void backToLoginAction(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void registerClient(ActionEvent event) {
        if(unamefield.getText().isBlank() || passwordfield.getText().isBlank()) {
            errorLabel.setText("blank info ");
            errorLabel.setTextFill(Color.RED);
            return ;
        }
        if(passwordfield.getText().compareTo(cpasswordfield.getText()) != 0) {
            errorLabel.setText("Password mismatch");
            errorLabel.setTextFill(Color.RED);
            return ;
        }
        else {
            if(signUpModel.registerClient(unamefield.getText(),passwordfield.getText()) == 1) {
                errorLabel.setText("Registered");
                errorLabel.setTextFill(Color.GREEN);
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("Login!");
                stage.setScene(scene);
                stage.show();
            }
            else {
                errorLabel.setText("username taken");
                errorLabel.setTextFill(Color.RED);
            }
        }
    }
}