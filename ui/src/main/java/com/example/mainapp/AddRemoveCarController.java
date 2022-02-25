package com.example.mainapp;

import Models.AddRemoveCarModel;
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
import java.util.concurrent.atomic.AtomicReference;

public class AddRemoveCarController {

    final private AddRemoveCarModel addRemoveCarModel = new AddRemoveCarModel();

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Label labelAdd;
    @FXML
    private Label labelRemove;
    @FXML
    private TextField txtBranch;
    @FXML
    private TextField txtCarId;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtYear;

    private void resetTextFields(TextField... t) {
        for (TextField textField : t) {
            textField.setText("");
        }
    }

    private void resetLabel(Label l) {
        l.setText("");
    }

    @FXML
    void AddCarClicked() {
        if (txtBranch.getText().isEmpty() || txtColor.getText().isEmpty() || txtYear.getText().isEmpty() || txtModel.getText().isEmpty() || txtPrice.getText().isEmpty() || txtType.getText().isEmpty()) {
            labelAdd.setText("Missing info");
        } else {
            addRemoveCarModel.AddCar(txtBranch.getText(), txtColor.getText(), txtYear.getText(), txtModel.getText(), txtPrice.getText(), txtType.getText());
            labelAdd.setText("Car is added successfully");
        }
        resetLabel(labelAdd);
        resetTextFields(txtBranch, txtColor, txtYear, txtModel, txtPrice, txtType);
    }

    public void RemoveCarClicked() {
        int errorCode;
        AtomicReference<String> msg = new AtomicReference<>("");
        if (txtCarId.getText().isEmpty()) {
            labelRemove.setText("Missing info");
        } else {
            errorCode = addRemoveCarModel.RemoveCar(txtCarId.getText());
            if (errorCode == 0) {
                msg.set("No car with such ID to be removed");
            } else if (errorCode == 1) {
                msg.set("The car is removed successfully");
            }
            labelRemove.setText(msg.get());
            txtCarId.setText("");
        }
        labelAdd.setText("");
    }

    public void BackClicked(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Car Mode");
        stage.setScene(scene);
        stage.show();
    }
}
