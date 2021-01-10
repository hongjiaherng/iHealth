package controllers;

import dao.PatientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Appointment;
import models.Patient;
import utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientCheckAppointmentController implements Initializable {

    @FXML
    private RadioButton listAppointmentRadioButton;
    @FXML
    private MenuItem cancelMenuItem;
    @FXML
    private TableView<Appointment> table;

    @FXML
    private TableColumn<Appointment, String> usernameCol;

    @FXML
    private TableColumn<Appointment, String> reasonCol;

    @FXML
    private TableColumn<Appointment, String> confirmDateCol;

    @FXML
    private TableColumn<Appointment, String> bookedTimeCol;

    @FXML
    private TableColumn<Appointment, String> remarksCol;

    ObservableList<Appointment> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getSessionUser().getUsername();
        Patient currentPatient = PatientDao.findAppointments(username);

        if (currentPatient != null) {
            loadAppointments(currentPatient);
        }
    }

    @FXML
    private void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent checkAppointmentRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene checkAppointmentScene = new Scene(checkAppointmentRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(checkAppointmentScene);
        appStage.show();
    }

    @FXML
    private void rightClickToDelete(MouseEvent mouseEvent) {
        if (!obList.isEmpty()) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                cancelMenuItem.setOnAction(e -> {
                    deleteConfirmation();
                });
            }
        } else {
            cancelMenuItem.setDisable(true);
        }
    }

    @FXML
    private void deleteAppointmentOnAction(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            deleteConfirmation();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setContentText("Please select an appointment");
            errorAlert.show();
        }
        table.getSelectionModel().clearSelection();
    }

    private void deleteConfirmation() {
        Appointment item = table.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(PatientCheckAppointmentController.class.getResourceAsStream("../res/images/patient.png")));
        alert.setTitle("Cancel Appointment");
        alert.setHeaderText("Confirm to cancel appointment?");
        alert.setContentText(String.format("Appointment details\n\n%10s : %s\n%10s : %s\n%8s : %s", "Date", item.getConfirmDate(), "Time", item.getBookedTime(), "Reason", item.getReason()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                table.getItems().remove(item);
                Patient patient = PatientDao.removeSelectedAppointment(item);
            }
        }
    }

    public void listAppointmentInAWeek(ActionEvent actionEvent) {

        String username = SessionManager.getSessionUser().getUsername();
        Patient currentPatient = PatientDao.findAppointments(username);

        if (listAppointmentRadioButton.isSelected()) {
            table.getItems().clear();
            List<LocalDate> datesWithinAWeek = PatientDao.findDatesWithinAWeek(username, LocalDate.now());

            if (currentPatient != null) {
                for (int i = 0; i < currentPatient.getReason().size(); i++) {
                    String reason = currentPatient.getReason().get(i);
                    String confirmDate = currentPatient.getConfirmDate().get(i);
                    String bookedTime = currentPatient.getBookedTime().get(i);
                    String remark = currentPatient.getRemarks().get(i);

                    for (LocalDate appointmentDate: datesWithinAWeek) {
                        if (appointmentDate.toString().equals(confirmDate)) {
                            Appointment appointment = new Appointment(username, reason, confirmDate, bookedTime, remark);

                            obList.add(appointment);

                            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
                            reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
                            confirmDateCol.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));
                            bookedTimeCol.setCellValueFactory(new PropertyValueFactory<>("bookedTime"));
                            remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

                            table.setItems(obList);
                        }
                    }
                }
            }

        } else {
            loadAppointments(currentPatient);
        }
    }

    private void loadAppointments(Patient currentPatient) {
        table.getItems().clear();
        for (int i = 0; i < currentPatient.getReason().size(); i++) {
            String reason = currentPatient.getReason().get(i);
            String confirmDate = currentPatient.getConfirmDate().get(i);
            String bookedTime = currentPatient.getBookedTime().get(i);
            String remark = currentPatient.getRemarks().get(i);

            Appointment appointment = new Appointment(currentPatient.getUsername(), reason, confirmDate, bookedTime, remark);

            obList.add(appointment);

            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
            confirmDateCol.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));
            bookedTimeCol.setCellValueFactory(new PropertyValueFactory<>("bookedTime"));
            remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

            table.setItems(obList);
        }
    }
}
