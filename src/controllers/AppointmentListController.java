package controllers;

import dao.PatientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.AppointmentList;
import models.Patient;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppointmentListController implements Initializable{

    @FXML
    private TableView<AppointmentList> table;

    @FXML
    private TableColumn<AppointmentList, String> dateCol;

    @FXML
    private TableColumn<AppointmentList, String> timeCol;

    @FXML
    private TableColumn<AppointmentList, String> nameCol;

    @FXML
    private TableColumn<AppointmentList, String> icCol;

    @FXML
    private TableColumn<AppointmentList, String> reasonCol;

    @FXML
    private TableColumn<AppointmentList, String> remarkCol;

    @FXML
    private TextField icRow;

    @FXML
    private Label saveLabel;

    // A list that track changes in table view when it occur
    ObservableList<AppointmentList> list = FXCollections.observableArrayList();

    // Declare an AppointmentList object called selectedItem
    public static AppointmentList selectedItem;

    // Method to call the initRecord() method to initialize in the table view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initRecord();
    }

    // Method to search whether the IC or name entered is empty or valid
    @FXML
    private void search(ActionEvent actionEvent) throws IOException {
        String nameOrIc = icRow.getText();
        if (nameOrIc.isEmpty()) {
            saveLabel.setText("Please enter IC number or name!");
        } else if (!validateIcOrName(nameOrIc)) {
            saveLabel.setText("Invalid IC number or name!");
        } else {
            saveLabel.setText("");
            searchAppointment();
        }
    }

    // Method to validate the IC or name entered by the admin
    private boolean validateIcOrName(String nameOrIc) {
        Patient patient = PatientDao.findIcOrName(nameOrIc);
        if(patient == null){
            return false;
        }
        return true;
    }

    // Method that switch to admin main page if the back button is clicked
    @FXML
    private void backToAdminPage(ActionEvent actionEvent) throws IOException {
        Parent appointmentListRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
        Scene appointmentListScene = new Scene(appointmentListRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(appointmentListScene);
        appStage.show();
    }

    // Method that switch to edit appointment page if the edit button is clicked
    @FXML
    private void editButton(ActionEvent actionEvent) throws IOException {
        // Check if any record is selected, if yes popup edit view, if no popup error view
        if (table.getSelectionModel().getSelectedItem() != null) {

            selectedItem = table.getSelectionModel().getSelectedItem();

            Stage editAppointmentListWindow = new Stage();
            Parent editOperatingHoursRoot = FXMLLoader.load(getClass().getResource("../views/editAppointmentListView.fxml"));
            Scene editAppointmentListScene = new Scene(editOperatingHoursRoot);
            editAppointmentListWindow.setTitle("iHealth - Edit Appointment List");
            editAppointmentListWindow.setScene(editAppointmentListScene);
            editAppointmentListWindow.alwaysOnTopProperty();
            editAppointmentListWindow.initModality(Modality.APPLICATION_MODAL);
            editAppointmentListWindow.initStyle(StageStyle.UTILITY);
            editAppointmentListWindow.showAndWait();
            initRecord();

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setContentText("Please select a record");
            errorAlert.show();
        }
        table.getSelectionModel().clearSelection();
    }

    // Method to reset the table view to all patient appointment details
    @FXML
    private void reset(ActionEvent actionEvent) throws IOException{
        initRecord();
    }

    // Method to search the appointment when IC or name is entered
    private void searchAppointment() {
        table.getItems().clear();
        String icOrName = icRow.getText();
        Patient patient = PatientDao.findIcOrName(icOrName);
        List<AppointmentList> appointmentLists = new ArrayList<>();

        if (patient != null ) {
            for (int i = 0; i < patient.getReason().size(); i++) {
                String date = patient.getConfirmDate().get(i);
                String time = patient.getBookedTime().get(i);
                String reason = patient.getReason().get(i);
                String remark = patient.getRemarks().get(i);

                AppointmentList appointmentList = new AppointmentList(date, time, patient.getName(), patient.getIcNo(), reason, remark);
                appointmentLists.add(appointmentList);
            }
            // Sort the date in ascending order
            Collections.sort(appointmentLists);
            for (AppointmentList item : appointmentLists) {
                list.add(item);

                dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
                timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                icCol.setCellValueFactory(new PropertyValueFactory<>("ic"));
                reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
                remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));

                table.setItems(list);
            }
            appointmentLists.clear();
        }
    }

    // Method to display all the patient appointment details in table view
    private void initRecord(){
        table.getItems().clear();
        Iterator<Patient> allPatients = PatientDao.findAll();

        List<AppointmentList> appointmentLists = new ArrayList<>();

        if (allPatients != null ) {
            while (allPatients.hasNext()){
                Patient patient = allPatients.next();
                if(patient.getReason().size() != 0){
                    for (int i = 0; i < patient.getReason().size(); i++) {
                        String date = patient.getConfirmDate().get(i);
                        String time = patient.getBookedTime().get(i);
                        String reason = patient.getReason().get(i);
                        String remark = patient.getRemarks().get(i);

                        AppointmentList appointmentList = new AppointmentList(date, time, patient.getName(), patient.getIcNo(), reason, remark);
                        appointmentLists.add(appointmentList);
                    }
                    // Sort the date in ascending order
                    Collections.sort(appointmentLists);
                    for (AppointmentList item : appointmentLists) {
                        list.add(item);

                        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
                        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
                        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                        icCol.setCellValueFactory(new PropertyValueFactory<>("ic"));
                        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
                        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));

                        table.setItems(list);
                    }
                    appointmentLists.clear();
                }

            }

        }
    }

}