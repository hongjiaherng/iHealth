package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDiffOperatingHoursController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> openingTimeChoiceBox;

    @FXML
    private ChoiceBox<String> closingTimeChoiceBox;

    @FXML
    private TextField remarkTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            openingTimeChoiceBox.getItems().add(time.toString());
            closingTimeChoiceBox.getItems().add(time.toString());
        }

    }

    @FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (datePicker == null || openingTimeChoiceBox == null || closingTimeChoiceBox == null || remarkTextField == null) {

        }
    }

    @FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        Stage addDiffOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addDiffOperatingHoursWindow.close();
    }


}
