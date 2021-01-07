package controllers;

import dao.PatientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;
import models.Patient;
import utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientCheckAppointmentController implements Initializable {

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
        Patient currentPatient = PatientDao.findAppointment(username);

        if (currentPatient != null) {
            for (int i = 0; i < currentPatient.getReason().size(); i++) {
                String reason = currentPatient.getReason().get(i);
                String confirmDate = currentPatient.getConfirmDate().get(i);
                String bookedTime = currentPatient.getBookedTime().get(i);
                String remark = currentPatient.getRemarks().get(i);

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

    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent checkAppointmentRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
        Scene checkAppointmentScene = new Scene(checkAppointmentRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(checkAppointmentScene);
        appStage.show();
    }
}
