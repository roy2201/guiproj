package com.example.mainapp;

import Models.ChannelModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ChannelController implements Initializable {

    private final ChannelModel model;

    @FXML
    private ListView<String> commentListView;
    @FXML
    private TextField commentBox;
    @FXML
    private Label errorLabel;

    public ChannelController() {
        System.out.println("Constructor ++++++++++++");
        model = new ChannelModel();
        Customer ct = new Customer(model);
        model.addObserver(ct);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commentListView.getItems().addAll(model.getComments());
    }

    @FXML
    public void postCommentAction() {
        String comment = commentBox.getText();
        if(comment.isBlank()) {
            errorLabel.setText("Cannot post blank comment");
            errorLabel.setTextFill(Color.RED);
        } else {
            model.addComment(commentBox.getText());
            errorLabel.setText("Comment Posted !");
            errorLabel.setTextFill(Color.GREEN);
        }
    }

    @FXML
    public void saveCommentAction() {
        if(!commentBox.getText().isBlank()) {
            String comment = commentBox.getText();
            model.saveComment(comment);
            errorLabel.setText("Saved");
            errorLabel.setTextFill(Color.GREEN);
        } else {
            errorLabel.setText("No comment entered");
            errorLabel.setTextFill(Color.RED);
        }
    }

    /* NAVIGATION FUNCTIONS */
    @FXML
    public void backAction(MouseEvent event) {
        model.clearComment();
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

    @FXML
    public void profileAction(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile-view.fxml"));
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
    /* end of navigation functions */

    /* inner class customer */
    private class Customer implements Observer {

        final private ChannelModel model;

        Customer(ChannelModel model) {
            this.model = model;
        }

        @Override
        public void update(Observable o, Object arg) {
            if (o == model) {
                System.out.println("I am updating");
                commentListView.getItems().clear();
                commentListView.getItems().addAll(model.getComments());
            }
        }
    }

}