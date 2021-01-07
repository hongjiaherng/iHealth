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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private String visitReason;
    private String time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeChoiceBox.getItems().add("");
    }

    @FXML
    public void pickDateOnAction(ActionEvent actionEvent) {

        String openingTime, closingTime;
        timeChoiceBox.getItems().clear();
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
        }

        for (int temp = Integer.parseInt(openingTime); temp < Integer.parseInt(closingTime); temp += 100) {
            StringBuilder timeSelection = new StringBuilder(String.valueOf(temp));
            if (timeSelection.length() == 3) {
                timeSelection.insert(0, "0");
            }
            timeChoiceBox.getItems().add(timeSelection.toString());
        }
    }

    @FXML
    public void bookNowOnAction(ActionEvent actionEvent) throws IOException {

        String username = SessionManager.getSessionUser().getUsername();

        if (date == null || visitReasonTextField.getText().isEmpty() || timeChoiceBox.getValue() == null) {
            bookFailLabel.setText("Please fill up all the fields");
        } else {
            bookFailLabel.setText("");
            date = datePicker.getValue().toString();
            visitReason = visitReasonTextField.getText();
            time = timeChoiceBox.getValue();

            boolean timeSlotAvailable = PatientDao.isAvailable(date, time);

            if (timeSlotAvailable) {
                Patient patient = PatientDao.bookAppointment(username, date, time, visitReason);

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
    public void cancelOnAction(ActionEvent actionEvent) {
        visitReasonTextField.setText("");
        datePicker.getEditor().clear();
        timeChoiceBox.getItems().clear();
        diffOperatingHoursLabel.setText("");
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent patientMainPageRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene patientMainPageScene = new Scene(patientMainPageRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(patientMainPageScene);
        appStage.show();
    }
}