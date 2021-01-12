package controllers;

import dao.OperatingDetailsDao;
import dao.PatientDao;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.OperatingDetails;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

// A controller class for an extra window that can add different operating details

public class AddDiffOperatingHoursController implements Initializable {

    @FXML
    private Label errorMessageLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> openingTimeChoiceBox;

    @FXML
    private ChoiceBox<String> closingTimeChoiceBox;

    @FXML
    private TextField remarkTextField;

    // Initialize the items in the opening and closing time choice box

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initChoiceBoxItems();
    }

    // Helps to disable other controls when a past date has been selected
    @FXML
    private void pickDateOnAction(ActionEvent actionEvent) {
        if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {
            errorMessageLabel.setText("");
            openingTimeChoiceBox.setDisable(false);
            closingTimeChoiceBox.setDisable(false);
            remarkTextField.setDisable(false);
        } else {
            errorMessageLabel.setText("You can't choose the past date");
            openingTimeChoiceBox.setDisable(true);
            closingTimeChoiceBox.setDisable(true);
            remarkTextField.setDisable(true);
        }
    }

    // Save the current changes to MongoDB
    @FXML
    private void saveChangesOnAction(ActionEvent actionEvent) {
        if (datePicker.getValue() == null || openingTimeChoiceBox.getValue() == null || closingTimeChoiceBox.getValue() == null || remarkTextField.getText().isEmpty()) {
            errorMessageLabel.setText("The fields can't be empty");
            clearFields();
        } else {

            String date = datePicker.getValue().toString();
            String openingTime = openingTimeChoiceBox.getValue();
            String closingTime = closingTimeChoiceBox.getValue();
            String remark = remarkTextField.getText();
            OperatingDetails operatingDetails = new OperatingDetails();
            operatingDetails.setDate(date).setOpeningTime(openingTime).setClosingTime(closingTime).setRemark(remark);

            boolean isBooked = PatientDao.dateIsBooked(operatingDetails);
            boolean dateIsSet = OperatingDetailsDao.dateIsSet(operatingDetails);
            if (dateIsSet) {
                // Check if the date had been set, if yes, can't add anymore
                errorMessageLabel.setText("You had already changed operating hours of this day, please edit it instead");
                clearFields();
            } else if (isBooked) {
                // Check if the date had been booked, if yes, can't add the time anymore
                errorMessageLabel.setText("Some patients had already made appointment on this day, you can't change the operating hours anymore");
                clearFields();
            } else {
                OperatingDetailsDao.setOperatingHours(operatingDetails);

                errorMessageLabel.setTextFill(Color.DARKBLUE);
                errorMessageLabel.setText("Operating details are set successfully!");

                PauseTransition delay = new PauseTransition(Duration.millis(500));
                delay.setOnFinished(
                        e -> {
                            // Back to login page
                            Stage addDiffOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            addDiffOperatingHoursWindow.close();
                        }
                );
                delay.play();
            }
        }
    }

    // Close the window and back to clinicDetailsView
    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        Stage addDiffOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addDiffOperatingHoursWindow.close();
    }

    // Reset the items in opening time choice box
    @FXML
    private void openingTimeChoiceBoxReset(MouseEvent mouseEvent) {
        if (closingTimeChoiceBox.getValue() != null) {
            openingTimeChoiceBox.getItems().clear();
            String closingTime = closingTimeChoiceBox.getValue();
            if (closingTime.equals("0000")) {
                closingTime = "2400";
            }
            String openingTime = String.valueOf(Integer.parseInt(closingTime) - 100);

            List<String> time = new ArrayList<>();
            for (int temp = Integer.parseInt(openingTime); temp >= 800; temp -= 100) {
                if (String.valueOf(temp).length() == 3) {
                    time.add("0" + temp);
                } else {
                    time.add(String.valueOf(temp));
                }
            }
            time.sort(Comparator.naturalOrder());
            for (String item : time) {
                openingTimeChoiceBox.getItems().add(item);
            }
        }
    }

    // Reset the items in closing time choice box
    @FXML
    private void closingTimeChoiceBoxReset(MouseEvent mouseEvent) {
        if (openingTimeChoiceBox.getValue() != null) {
            closingTimeChoiceBox.getItems().clear();
            String openingTime = openingTimeChoiceBox.getValue();
            String closingTime = String.valueOf(Integer.parseInt(openingTime) + 100);
            for (int temp = Integer.parseInt(closingTime); temp <= 2400; temp += 100) {
                StringBuilder time = new StringBuilder(String.valueOf(temp));
                if (time.length() == 3) {
                    time.insert(0, "0");
                } else if (time.toString().equals("2400")) {
                    time = new StringBuilder("0000");
                }
                closingTimeChoiceBox.getItems().add(time.toString());
            }
        }
    }

    // Helper method to initialize the choice box items
    private void initChoiceBoxItems() {
        String operatingTime = "0800"; // 0800 - 2300
        for (int temp = Integer.parseInt(operatingTime); temp <= 2400; temp += 100) {
            StringBuilder time;

            if (temp == 2400) {
                time = new StringBuilder("0000");
            } else {
                time = new StringBuilder(String.valueOf(temp));
            }
            if (time.length() == 3) {
                time.insert(0, "0");
            }
            if (time.toString().equals("0000")) {
                closingTimeChoiceBox.getItems().add(time.toString());
            } else if (time.toString().equals("0800")){
                openingTimeChoiceBox.getItems().add(time.toString());
            } else {
                openingTimeChoiceBox.getItems().add(time.toString());
                closingTimeChoiceBox.getItems().add(time.toString());
            }
        }
    }

    // Helper method to clear all the filled up fields
    private void clearFields() {
        datePicker.getEditor().clear();
        openingTimeChoiceBox.getItems().clear();
        closingTimeChoiceBox.getItems().clear();
        remarkTextField.clear();
        initChoiceBoxItems();
    }


}
