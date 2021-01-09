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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.OperatingDetails;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ClinicDetailsController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initRecords();
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
