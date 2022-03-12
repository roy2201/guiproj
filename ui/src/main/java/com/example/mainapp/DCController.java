package com.example.mainapp;

import Models.CheckProxy;
import Models.DCModel;
import Models.LoginModel;
import Models.PaymentContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DCController implements Initializable {

    @FXML
    private Spinner<Integer> daysToRentSpinner;
    @FXML
    private ListView<String> propListView;
    @FXML
    private Label errorLabel;

    static int total;
    static boolean isPremium;

    //initialize list view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DCModel dcModel = new DCModel();
        int carid = DCModel.getCarId();
        ArrayList<String> properties= new ArrayList<>();
        try {
            ResultSet rs = dcModel.loadCarInformation(carid);
            while(rs.next()) {
                properties.add("car-model-id : A330"+rs.getString("crid"));
                properties.add("Color : "+rs.getString("ccolor"));
                properties.add("Located in : "+rs.getString("bname"));
                properties.add("Year : "+String.valueOf(rs.getInt("cyear")));
                properties.add("Car Model : "+rs.getString("cmodel"));
                properties.add("Type : "+rs.getString("ctype"));
                properties.add("Price / day : "+String.valueOf(rs.getInt("cprice")+"$"));
                //** load some random values in the list view
                if(carid % 2 == 0) {
                    properties.add("Doors : 4");
                    properties.add("Class : Economy");
                } else {
                    properties.add("doors : 2");
                    properties.add("class : Compact");
                }
                properties.add("Rented times : "+ (int) (Math.random() * 15) );
                properties.add("Nb of Passengers  "+ (int) (2 + Math.random() * 4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        propListView.getItems().addAll(properties);
        //** activate spinner // ** max rent days 365
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365);
        valueFactory.setValue(1);
        daysToRentSpinner.setValueFactory(valueFactory);

    }

    //nav functions
    @FXML
    public void commentAction(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("channel-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Comment Channel");
        stage.setScene(scene);
        stage.show();
    }

    public void backAction2(MouseEvent event) {
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
    }

    private void viewReceiptAction(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("payment-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Receipt and Payment");
        stage.setScene(scene);
        stage.show();
    }
    //end nav

    //uses proxy => create a proxy object and give it amount should be paid
    //it will handle the creation of new window in case not enough balance detected.
    @FXML
    public void checkBalance(MouseEvent event) {
        CheckProxy cp = new CheckProxy(getAmount());
        if(cp.checkBalance()) {
            //System.out.println("Enough balance");
            viewReceiptAction(event);
        } else {
            errorLabel.setText("Insufficient balance detection\n" +
                                "Canceling redirection to payment");
        }
    }


    //return total amount should be paid
    //initialize static values
    private int getAmount() {
        int discount, nbDays, carId;
        PaymentContext paymentContext = new PaymentContext();
        DCModel.setNbDays(daysToRentSpinner.getValue());
        nbDays = DCModel.getNbDays();
        carId = DCModel.getCarId();
        //System.out.println("days are " + nbDays);
        //System.out.println("id is " + carId);
        total = paymentContext.calculateTotal(carId, nbDays);
        //System.out.println("total " + total);
        isPremium = paymentContext.checkPremium(LoginModel.getLogged());
        if(isPremium) {
            discount = (int) (total * 0.25);
            //System.out.println("discount " + discount);
        } else {
            discount = 0;
        }
        return total - discount;
    }

}
