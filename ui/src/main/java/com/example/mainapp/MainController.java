package com.example.mainapp;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MainController implements Initializable {

    private LoginModel loginModel = new LoginModel();
    private MainModel mainModel = new MainModel();

    @FXML
    private ImageView profileImageView;
    @FXML
    private TableView<CarModel> carsTable;
    @FXML
    private ComboBox<String> locationComboBox;
    @FXML
    private ComboBox<String> modelComboBox;
    @FXML
    private TableColumn<CarModel, Integer> caridColumn;
    @FXML
    private TableColumn<CarModel, String> typeColumn;
    @FXML
    private TableColumn<CarModel, String> modelColumn;
    @FXML
    private TableColumn<CarModel, Integer> priceColumn;
    @FXML
    private TableColumn<CarModel, Integer> yearColumn;
    @FXML
    private TableColumn<CarModel, String> colorColumn;
    @FXML
    private TableColumn<CarModel, String> locationColumn;
    @FXML
    private Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAllCars();
        viewAllBranches();
        viewAllModels();
    }

    @FXML
    public void subscribe(ActionEvent event) {
        errorLabel.setText("You are now subscribed");
        errorLabel.setTextFill(Color.PURPLE);
        /** add customer object to file / try it with one customer **/
        /** mainModel.serializeCustomer(customer); **/
        /** customer is added to binary file **/
        /** each time a login is make we add a customer to the file **/
        /** use equals method to compare loaded object with current object **/
        /** if not equals then add , otherwise don't **/
        //Customer ct = LoginController.getCustomerObject();
        serializeCustomer();
    }

    public void serializeCustomer() {
    }

    @FXML
    public void detailedViewAction(ActionEvent event) {
        System.out.println("clicked");
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detailed-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Your profile");
        stage.setScene(scene);
        stage.show();
    }

    public void viewAllCars()  {
        carsTable.getColumns().clear();
        ResultSet rs = null;
        errorLabel.setText("");
        try {
            rs = mainModel.loadCars();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        caridColumn.setCellValueFactory(new PropertyValueFactory<>("carid"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        carsTable.getColumns().addAll(caridColumn, modelColumn, typeColumn, yearColumn, colorColumn, locationColumn, priceColumn);
        ObservableList<CarModel> car = FXCollections.observableArrayList();
        while (true) {
            try {
                if (!rs.next()) break;
                System.out.println("++++++++++++++++++++++test");
                car.add(new CarModel(rs.getInt("crid"), rs.getString("ccolor"), rs.getString("cmodel"), rs.getString("ctype"), rs.getString("bname"), rs.getInt("cyear"), rs.getInt("cprice")));
                carsTable.setItems(car);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void filterResults(ActionEvent event) {
        String errorString = "";
        carsTable.getColumns().clear();
        ResultSet rs = null;
        errorLabel.setText("");
        if(locationComboBox.getValue() == null) {
            errorString += "please choose a location";
        }
        if(modelComboBox.getValue() == null) {
            errorString += "\nplease choose a model";
        }
        if(errorString != "") {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText(errorString);
            return ;
        }
        try {
            rs = mainModel.loadFilteredResults(modelComboBox.getValue().toString(), locationComboBox.getValue().toString());
            caridColumn.setCellValueFactory(new PropertyValueFactory<CarModel, Integer>("carid"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("model"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("type"));
            yearColumn.setCellValueFactory(new PropertyValueFactory<CarModel, Integer>("year"));
            colorColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("color"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("location"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<CarModel, Integer>("price"));
            carsTable.getColumns().addAll(caridColumn, modelColumn, typeColumn, yearColumn, colorColumn, locationColumn, priceColumn);
            ObservableList<CarModel> car = FXCollections.observableArrayList();
            boolean found = false;
            while (true) {
                try {
                    if (!rs.next()) break;
                    car.add(new CarModel(rs.getInt(1), rs.getString("ccolor"), rs.getString("cmodel"), rs.getString("ctype"), rs.getString("bname"), rs.getInt("cyear"), rs.getInt("cprice")));
                    carsTable.setItems(car);
                    System.out.println("-----------------test");
                    found = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!found) {
                errorLabel.setText("No matches");
                errorLabel.setTextFill(Color.RED);
            } else {
                errorLabel.setText("Found matches");
                errorLabel.setTextFill(Color.GREEN);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllBranches() {
        ResultSet rs = null;
        ArrayList<String> branches = new ArrayList<String>();
        try {
            rs = mainModel.loadBranches();
            while (rs.next()) {
                branches.add(rs.getString("bname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        locationComboBox.getItems().addAll(branches);
    }

    public void viewAllModels() {
        ResultSet rs = null;
        ArrayList<String> models = new ArrayList<>();
        try {
            rs = mainModel.loadModels();
            while (rs.next()) {
                models.add(rs.getString("cmodel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modelComboBox.getItems().addAll(models);
    }

    @FXML
    void handleProceed(MouseEvent event) {
        errorLabel.setText("");
        CarModel c = carsTable.getSelectionModel().getSelectedItem();
        if(c == null) {
            errorLabel.setText("Please select a car !");
        } else {
            // save carid then go to detailed view
            DCModel.setCid(c.getCarid());
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detailed-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Your Car");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void handle(MouseEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Your profile");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleLogout(MouseEvent event) {
        int cid = loginModel.getLogged();
        loginModel.logout(cid);
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
}