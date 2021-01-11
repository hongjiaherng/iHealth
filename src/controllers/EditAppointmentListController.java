package controllers;

import dao.PatientDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.AppointmentList;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAppointmentListController implements Initializable {

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField timeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField icTextField;

    @FXML
    private TextField reasonTextField;

    @FXML
    private TextField remarkTextField;

    @FXML
    private Label messageLabel;

    // Declare an AppointmentList object called item
    private AppointmentList item;

    // Method to initialize the details of the selected patient appointment in text field
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        item = AppointmentListController.selectedItem;
        dateTextField.setText(item.getDate());
        timeTextField.setText(item.getTime());
        nameTextField.setText(item.getName());
        icTextField.setText(item.getIc());
        reasonTextField.setText(item.getReason());
        remarkTextField.setText(item.getRemark());
    }

    // Method to save remark into patient appointment details
    public void save(ActionEvent actionEvent) {
        String remark = remarkTextField.getText();

        AppointmentList appointmentList = new AppointmentList();
        appointmentList.setRemark(remark);
        PatientDao.editAppointmentList(item, appointmentList);

        messageLabel.setText("Appointment List is changed successfully!");
        // Back to appointment list page
        Stage editOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editOperatingHoursWindow.close();
    }

    // Method to cancel the edit appointment
    public void cancel(ActionEvent actionEvent) {
        Stage editOperatingHoursWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editOperatingHoursWindow.close();
    }
}
