package controllers;

import dao.OperatingDetailsDao;
import dao.PatientDao;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import models.Appointment;
import models.OperatingDetails;
import models.Patient;
import utils.SessionManager;

public class PatientMakeAppointmentController implements Initializable {

    @FXML
    private Label diffOperatingHoursLabel;
    @FXML
    private Label bookFailLabel;
    @FXML
    private ChoiceBox<String> timeChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField visitReasonTextField;

    private String date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeChoiceBox.getItems().add("");
    }

    @FXML
    private void pickDateOnAction(ActionEvent actionEvent) {

        diffOperatingHoursLabel.setText("");

        String openingTime, closingTime;
        timeChoiceBox.getItems().clear();

        if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {

            visitReasonTextField.setDisable(false);
            bookFailLabel.setText("");
            date = datePicker.getValue().toString();

            OperatingDetails operatingDetails = OperatingDetailsDao.findOperatingHours(date);

            if (operatingDetails == null) {
                // Default opening and closing time
                openingTime = "0800";
                closingTime = "1800";
                diffOperatingHoursLabel.setText("");
            } else {
                // Follow the specified opening and closing time
                openingTime = operatingDetails.getOpeningTime();
                closingTime = operatingDetails.getClosingTime();
                diffOperatingHoursLabel.setText("Operating hours for today is from " + openingTime + " to " + closingTime + " due to some adjustment by clinic staff");
                if (closingTime.equals("0000")) {
                    closingTime = "2400";
                }
            }

            for (int temp = Integer.parseInt(openingTime); temp < Integer.parseInt(closingTime); temp += 100) {
                StringBuilder timeSelection = new StringBuilder(String.valueOf(temp));
                if (timeSelection.length() == 3) {
                    timeSelection.insert(0, "0");
                }
                timeChoiceBox.getItems().add(timeSelection.toString());
            }
        } else {
            bookFailLabel.setText("You can't choose the past date");
            visitReasonTextField.setDisable(true);
        }
    }

    @FXML
    private void bookNowOnAction(ActionEvent actionEvent) throws IOException {

        String username = SessionManager.getSessionUser().getUsername();

        if (date == null || visitReasonTextField.getText().isEmpty() || timeChoiceBox.getValue() == null) {
            bookFailLabel.setText("Please fill up all the fields");
//        } else if (date.compareTo(Instant.now().toString()) <= 0) {
//
        } else {
            bookFailLabel.setText("");
            date = datePicker.getValue().toString();
            String visitReason = visitReasonTextField.getText();
            String time = timeChoiceBox.getValue();

            boolean timeSlotAvailable = PatientDao.isAvailable(date, time);

            if (timeSlotAvailable) {

                Appointment appointment = new Appointment(username, visitReason, date, time, "");
                Patient patient = PatientDao.bookAppointment(appointment);

                if (patient != null) {
                    Parent bookSuccessRoot = FXMLLoader.load(getClass().getResource("../views/bookSuccessView.fxml"));
                    Scene bookSuccessScene = new Scene(bookSuccessRoot);
                    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    appStage.setScene(bookSuccessScene);
                    appStage.show();
                }

            } else {
                bookFailLabel.setText("Time slot had been booked by others");
            }
        }
    }


    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        visitReasonTextField.setText("");
        datePicker.getEditor().clear();
        timeChoiceBox.getItems().clear();
        diffOperatingHoursLabel.setText("");
    }

    @FXML
    private void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent patientMainPageRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene patientMainPageScene = new Scene(patientMainPageRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(patientMainPageScene);
        appStage.show();
    }
}