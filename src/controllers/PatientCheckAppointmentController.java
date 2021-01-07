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
    private TableView<Patient> table;

    @FXML
    private TableColumn<Patient, String> usernameCol;

    @FXML
    private TableColumn<Patient, String> reasonCol;

    @FXML
    private TableColumn<Patient, String> confirmDateCol;

    @FXML
    private TableColumn<Patient, String> bookedTimeCol;

    @FXML
    private TableColumn<Patient, String> remarksCol;

    ObservableList<Patient> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String username = SessionManager.getSessionUser().getUsername();
        Patient currentPatient = PatientDao.findAppointment(username);

        oblist.add(currentPatient);
        oblist.add(currentPatient);

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        confirmDateCol.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));
        bookedTimeCol.setCellValueFactory(new PropertyValueFactory<>("bookedTime"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

//        reasonCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
//                return p.getValue().new SimpleStringProperty();
//            }
//        });

        table.setItems(oblist);
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
