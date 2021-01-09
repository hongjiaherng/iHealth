package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientMainPageController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome, " + SessionManager.getSessionUser().getName() + "!");
    }

    @FXML
    private void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent patientLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        Scene patientLoginScene = new Scene(patientLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(patientLoginScene);
        appStage.show();
    }

    @FXML
    private void makeReservationOnAction(MouseEvent actionEvent) throws IOException {
        Parent makeAppointmentRoot = FXMLLoader.load(getClass().getResource("../views/patientMakeAppointmentView.fxml"));
        Scene makeAppointmentScene = new Scene(makeAppointmentRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(makeAppointmentScene);
        appStage.show();
    }

    @FXML
    private void checkDetailsOnAction(MouseEvent actionEvent) throws IOException {
        Parent checkDetailsRoot = FXMLLoader.load(getClass().getResource("../views/patientCheckAppointmentView.fxml"));
        Scene checkDetailsScene = new Scene(checkDetailsRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(checkDetailsScene);
        appStage.show();
    }
}
