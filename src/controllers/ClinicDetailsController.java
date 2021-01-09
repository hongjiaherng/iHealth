package controllers;

import dao.OperatingDetailsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.OperatingDetails;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;

// clear filter after both add and edit

public class ClinicDetailsController implements Initializable {

    @FXML
    private ChoiceBox<String> monthChoiceBox;

    @FXML
    private ChoiceBox<String> yearChoiceBox;

    @FXML
    private TableView<OperatingDetails> table;

    @FXML
    private TableColumn<OperatingDetails, String> dateCol;

    @FXML
    private TableColumn<OperatingDetails, String> openingTimeCol;

    @FXML
    private TableColumn<OperatingDetails, String> closingTimeCol;

    @FXML
    private TableColumn<OperatingDetails, String> remarkCol;

    ObservableList<OperatingDetails> obList = FXCollections.observableArrayList();

    public static OperatingDetails selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initRecords();
        for (int year = 2020; year <= 2025; year++) {
            yearChoiceBox.getItems().add(String.valueOf(year));
        }
        for (int month = 1; month <= 12; month++) {
            String monthStr = String.valueOf(month);
            if (monthStr.length() == 1) {
                monthStr = "0" + monthStr;
            }
            monthChoiceBox.getItems().add(monthStr);
        }
        monthChoiceBox.setDisable(true);
    }

    @FXML
    private void backAdminMainPage(ActionEvent actionEvent) throws IOException {
        Parent adminMainPageRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
        Scene adminMainPageScene = new Scene(adminMainPageRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(adminMainPageScene);
        appStage.show();
    }

    @FXML
    private void addDiffOperatingHours(ActionEvent actionEvent) throws IOException {
        Stage addDiffOperatingHoursWindow = new Stage();
        Parent addDiffOperatingHoursRoot = FXMLLoader.load(getClass().getResource("../views/addDiffOperatingHoursView.fxml"));
        Scene addDiffOperatingHoursScene = new Scene(addDiffOperatingHoursRoot);
        addDiffOperatingHoursWindow.setTitle("iHealth - Set Clinic's Operating Hours");
        addDiffOperatingHoursWindow.setScene(addDiffOperatingHoursScene);
        addDiffOperatingHoursWindow.alwaysOnTopProperty();
        addDiffOperatingHoursWindow.initModality(Modality.APPLICATION_MODAL);
        addDiffOperatingHoursWindow.initStyle(StageStyle.UTILITY);
        addDiffOperatingHoursWindow.showAndWait();
        table.getItems().clear();
        monthChoiceBox.getSelectionModel().clearSelection();
        yearChoiceBox.getSelectionModel().clearSelection();
        initRecords();
    }

    @FXML
    private void editOperatingHours(ActionEvent actionEvent) throws IOException {
        // Check if any record is selected, if yes popup edit view, if no popup error view
        if (table.getSelectionModel().getSelectedItem() != null) {

            selectedItem = table.getSelectionModel().getSelectedItem();

            Stage editOperatingHoursWindow = new Stage();
            Parent editOperatingHoursRoot = FXMLLoader.load(getClass().getResource("../views/editOperatingHoursView.fxml"));
            Scene editOperatingHoursScene = new Scene(editOperatingHoursRoot);
            editOperatingHoursWindow.setTitle("iHealth - Edit Clinic's Operating Hours");
            editOperatingHoursWindow.setScene(editOperatingHoursScene);
            editOperatingHoursWindow.alwaysOnTopProperty();
            editOperatingHoursWindow.initModality(Modality.APPLICATION_MODAL);
            editOperatingHoursWindow.initStyle(StageStyle.UTILITY);
            editOperatingHoursWindow.showAndWait();
            table.getItems().clear();
            monthChoiceBox.getSelectionModel().clearSelection();
            yearChoiceBox.getSelectionModel().clearSelection();
            initRecords();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setContentText("Please select a record");
            errorAlert.show();
        }
        table.getSelectionModel().clearSelection();
    }

    @FXML
    private void choiceBoxClicked(ActionEvent actionEvent) {
        if (yearChoiceBox.getValue() != null) {

            table.getItems().clear();
            monthChoiceBox.setDisable(false);
            Iterator<OperatingDetails> operatingDetailsIterator;
            OperatingDetails operatingDetails;

            String year = yearChoiceBox.getValue();

            if (monthChoiceBox.getValue() == null) {
                operatingDetailsIterator = OperatingDetailsDao.findAll();
                while (operatingDetailsIterator.hasNext()) {
                    operatingDetails = operatingDetailsIterator.next();
                    String obtainedYear = String.valueOf(LocalDate.parse(operatingDetails.getDate()).getYear());

                    if (year.equals(obtainedYear)) {
                        obList.add(operatingDetails);

                        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
                        openingTimeCol.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
                        closingTimeCol.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
                        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));

                        table.setItems(obList);
                    }
                }
            } else {
                int month = Integer.parseInt(monthChoiceBox.getValue());

                operatingDetailsIterator = OperatingDetailsDao.findAll();
                while (operatingDetailsIterator.hasNext()) {
                    operatingDetails = operatingDetailsIterator.next();
                    int obtainedMonth = LocalDate.parse(operatingDetails.getDate()).getMonth().getValue();
                    String obtainedYear = String.valueOf(LocalDate.parse(operatingDetails.getDate()).getYear());

                    if (month == obtainedMonth && year.equals(obtainedYear)) {
                        obList.add(operatingDetails);

                        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
                        openingTimeCol.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
                        closingTimeCol.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
                        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));

                        table.setItems(obList);
                    }
                }
            }
        }
    }

    @FXML
    private void clearYearMonthFilter(ActionEvent actionEvent) {
        monthChoiceBox.getSelectionModel().clearSelection();
        yearChoiceBox.getSelectionModel().clearSelection();
        table.getItems().clear();
        initRecords();
    }

    private void initRecords() {
        Iterator<OperatingDetails> operatingDetailsIterator = OperatingDetailsDao.findAll();
        while (operatingDetailsIterator.hasNext()) {

            OperatingDetails operatingDetails = operatingDetailsIterator.next();

            obList.add(operatingDetails);

            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            openingTimeCol.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
            closingTimeCol.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
            remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));

            table.setItems(obList);
        }
    }
}
