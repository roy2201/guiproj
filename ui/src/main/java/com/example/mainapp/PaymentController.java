package com.example.mainapp;

import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    private final PaymentContext paymentContext = new PaymentContext();

    @FXML
    private ComboBox<String> paymentMethodComboBox;
    @FXML
    private ListView<String> receiptListView;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField cardHolderNameTextField;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField expiryDateTextField;
    @FXML
    private TextField cvvTextField;
    @FXML
    private Label errorLabel;

    private int total;
    private boolean premium;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paymentMethodComboBox.getItems().addAll("CreditCard","Paypal");
        int nbDays = DCModel.getNbDays();
        int carId = DCModel.getCarId();
        total = paymentContext.calculateTotal(carId, nbDays);
        premium = paymentContext.checkPremium(LoginModel.getLogged());
        ArrayList<String> receipt = new ArrayList<>();
        receipt.add("Price : "+ total +" USD");
        float discount = 0.0F;
        if(premium){
            discount = (float) (total * 0.25);
            receipt.add("Premium : - 25 % : " + discount +" USD");
        } else {
            receipt.add("No premium : -  0 %" );
        }
        this.total = (int) (total - discount);
        receipt.add("total = "+ total + " USD");
        receiptListView.getItems().addAll(receipt);
    }

    /* strategy */
    @FXML
    public void payReceiptAction(ActionEvent event) {

        if (paymentMethodComboBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please selected payment method");
            errorLabel.setTextFill(Color.RED);
        } else if (paymentMethodComboBox.getValue().compareTo("CreditCard") == 0) {
            int creditCardNumber = Integer.parseInt(cardNumberTextField.getText());
            int cvv = Integer.parseInt(cvvTextField.getText());
            String expiryDate = expiryDateTextField.getText();
            String holderName = cardHolderNameTextField.getText();

            System.out.println("paying with credit card");
            CreditCardModel creditCardStrategy = new CreditCardModel(creditCardNumber, cvv, expiryDate, holderName);
            paymentContext.setPaymentStrategy(creditCardStrategy);
            if (creditCardStrategy.isValid(creditCardNumber, cvv, expiryDate, holderName)) {
                if(!paymentContext.execute(total)) {
                    errorLabel.setText("no enough balance");
                    errorLabel.setTextFill(Color.RED);
                    return ;
                }
                errorLabel.setText("Credit Card Transaction Successful");
                errorLabel.setTextFill(Color.GREEN);
            } else {
                errorLabel.setText("Invalid information");
                errorLabel.setTextFill(Color.RED);
            }
        } else if (paymentMethodComboBox.getValue().compareTo("Paypal") == 0) {
            String email, password;
            email = emailTextField.getText();
            password = passwordTextField.getText();
            System.out.println("paying with pay pal");
            PaypalModel paypalStrategy = new PaypalModel(email, password);
            paymentContext.setPaymentStrategy(paypalStrategy);
            if (paypalStrategy.validateInfo(email, password)) {
                if(!paymentContext.execute(total)) {
                    errorLabel.setText("no enough balance");
                    errorLabel.setTextFill(Color.RED);
                    return ;
                }
                errorLabel.setText("Paypal Transaction Successful");
                errorLabel.setTextFill(Color.GREEN);
            } else {
                errorLabel.setText("Invalid information");
                errorLabel.setTextFill(Color.RED);
            }
        }
    }

    /* navigation functions */
    @FXML
    void handleBack(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detailed-view.fxml"));
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

}