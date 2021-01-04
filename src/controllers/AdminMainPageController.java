package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminMainPageController {

    @FXML
    public void logOutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent adminLoginRoot = FXMLLoader.load(getClass().getResource("../views/adminLoginView.fxml"));
        Scene adminLoginScene = new Scene(adminLoginRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(adminLoginScene);
        appStage.show();
    }

    @FXML
    public void editAppoimentListButtonOnAction(ActionEvent actionEvent) throws IOException{
        Parent appoimentListRoot = FXMLLoader.load(getClass().getResource("../views/appointmentListView.fxml"));
        Scene appoimentListScene = new Scene(appoimentListRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(appoimentListScene);
        appStage.show();
    }

    @FXML
    public void editOperatingHoursButtonOnAction(ActionEvent actionEvent) throws IOException{
        Parent clinicDetailsRoot = FXMLLoader.load(getClass().getResource("../views/clinicDetailsView.fxml"));
        Scene clinicDetailsScene = new Scene(clinicDetailsRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(clinicDetailsScene);
        appStage.show();
    }
}
