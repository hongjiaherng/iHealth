package controllers;

import dao.OperatingDetailsDao;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.OperatingDetails;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

// A controller class for an extra window that can edit existing different operating details

public class EditOperatingHoursController implements Initializable {

    @FXML
    private RadioButton setCustomButton;
    @FXML
    private RadioButton setDefaultButton;
    @FXML
    private TextField dateTextField;
    @FXML
    private ChoiceBox<String> openingTimeChoiceBox;
    @FXML
    private ChoiceBox<String> closingTimeChoiceBox;
    @FXML
    private TextField remarkTextField;
    @FXML
    private Label errorMessageLabel;

    private OperatingDetails item;

    // Initialize the view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup buttonGroup = new ToggleGroup();
        setCustomButton.setToggleGroup(buttonGroup);
        setDefaultButton.setToggleGroup(buttonGroup);
        setCustomButton.setSelected(true);
        item = ClinicDetailsController.selectedItem;
        dateTextField.setText(item.getDate());
        openingTimeChoiceBox.setValue(item.getOpeningTime());
        closingTimeChoiceBox.setValue(item.getClosingTime());
        remarkTextField.setText(item.getRemark());
        initChoiceBox();
    }

    // Enable the controls for admin to fill up when setCustomButton is clicked
    @FXML
    private void setCustomOperatingHours(ActionEvent actionEvent) {
        if (setCustomButton.isSelected()) {
            dateTextField.setDisable(false);
            openingTimeChoiceBox.setDisable(false);
            closingTimeChoiceBox.setDisable(false);
            remarkTextField.setDisable(false);
        }
    }

    // Disable the controls for admin to fill up when setDefaultButton is clicked
    @FXML
    private void resetDefaultOperatingHours(ActionEvent actionEvent) {
        if (setDefaultButton.isSelected()) {
            dateTextField.setDisable(true);
            openingTimeChoiceBox.setDisable(true);
            closingTimeChoiceBox.setDisable(true);
            remarkTextField.setDisable(true);
            clearFields();
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

    // Save the changes to database
    @FXML
    private void saveChangesOnAction(ActionEvent actionEvent) {
        if (setCustomButton.isSelected()) {
            if (openingTimeChoiceBox.getValue() == null || closingTimeChoiceBox.getValue() == null || remarkTextField.getText().isEmpty()) {
                errorMessageLabel.setText("The fields can't be empty");
                clearFields();
                return;
            } else {

                String date = item.getDate();
                String openingTime = openingTimeChoiceBox.getValue();
                String closingTime = closingTimeChoiceBox.getValue();
                String remark = remarkTextField.getText();

                if (openingTime.equals(item.getOpeningTime()) && closingTime.equals(item.getClosingTime())) {
                    errorMessageLabel.setText("You are not making any changes to the time");
                    return;
                } else {
                    OperatingDetails operatingDetails = new OperatingDetails();
                    operatingDetails.setDate(date).setOpeningTime(openingTime).setClosingTime(closingTime).setRemark(remark);

                    OperatingDetailsDao.editOperatingHours(item, operatingDetails);

                    errorMessageLabel.setTextFill(Color.DARKBLUE);
                    errorMessageLabel.setText("Operating details are changed successfully!");
                }
            }
        } else if (setDefaultButton.isSelected()) {
            // delete the record from database
            OperatingDetailsDao.deleteOperatingDetails(item);
            errorMessageLabel.setTextFill(Color.DARKBLUE);
            errorMessageLabel.setText("Operating times for this day had been changed back to default");
        }

        // Back to clinic details page
        PauseTransition delay = new PauseTransition(Duration.millis(500));
        delay.setOnFinished(
                e -> {
                    // Back to login page
                    Stage editOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    editOperatingHoursWindow.close();
                }
        );
        delay.play();
    }

    // Close the curernt window and back to clinicDetailsView
    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        Stage editOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editOperatingHoursWindow.close();
    }

    // Helper method to initialize the choice box
    private void initChoiceBox() {

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

    // Helper method to clear the fields
    private void clearFields() {
        openingTimeChoiceBox.getItems().clear();
        closingTimeChoiceBox.getItems().clear();
        remarkTextField.clear();
        initChoiceBox();
    }
}
