package controllers;

import dao.CurrentOnlineUserDao;
import dao.PatientDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientMainPageController implements Initializable {

    @FXML
    private Hyperlink dateHyperlink;
    @FXML
    private Group notificationGroup;
    @FXML
    private Label welcomeLabel;

    // Method to display message "Welcome + nameOfPatient"
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome, " + SessionManager.getSessionUser().getName() + "!");

        notificationGroup.setVisible(false);
        // Check if any appointment within a week
        // If yes, show the rectangle
        List<LocalDate> datesWithinAWeek = PatientDao.findDatesWithinAWeek(SessionManager.getSessionUser().getUsername(), LocalDate.now());
        if (datesWithinAWeek.size() != 0) {
            notificationGroup.setVisible(true);

            // If only one appointment within a week, show the date as the hyperlink
            if (datesWithinAWeek.size() == 1) {
                dateHyperlink.setText(datesWithinAWeek.get(0).toString());
            } else {
                // Else show the number of appointment as the hyperlink
                dateHyperlink.setText(datesWithinAWeek.size() + " appointments");
            }
        }
    }

    // Method that switch to patient check appointment page when the hyperlink is clicked
    @FXML
    private void dateHyperlinkOnAction(ActionEvent actionEvent) throws IOException {
        Parent checkDetailsRoot = FXMLLoader.load(getClass().getResource("../views/patientCheckAppointmentView.fxml"));
        Scene checkDetailsScene = new Scene(checkDetailsRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(checkDetailsScene);
        appStage.show();
    }

    // Method that switch to patient login page when log out is clicked
    @FXML
    private void logoutOnAction(ActionEvent actionEvent) throws IOException {
        CurrentOnlineUserDao.destroyOnlineSession(SessionManager.getSessionUser().getUsername());
        Parent patientLoginRoot = FXMLLoader.load(getClass().getResource("../views/patientLoginView.fxml"));
        Scene patientLoginScene = new Scene(patientLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(patientLoginScene);
        appStage.show();
    }

    // Method that switch to patient make appointment page when make reservation button is clicked
    @FXML
    private void makeReservationOnAction(MouseEvent actionEvent) throws IOException {
        Parent makeAppointmentRoot = FXMLLoader.load(getClass().getResource("../views/patientMakeAppointmentView.fxml"));
        Scene makeAppointmentScene = new Scene(makeAppointmentRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(makeAppointmentScene);
        appStage.show();
    }

    // Method that switch to patient check appointment page when check appointment details button is clicked
    @FXML
    private void checkDetailsOnAction(MouseEvent actionEvent) throws IOException {
        Parent checkDetailsRoot = FXMLLoader.load(getClass().getResource("../views/patientCheckAppointmentView.fxml"));
        Scene checkDetailsScene = new Scene(checkDetailsRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(checkDetailsScene);
        appStage.show();
    }


}
