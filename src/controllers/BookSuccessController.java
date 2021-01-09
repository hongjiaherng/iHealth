package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;

    public class BookSuccessController {

        @FXML
        private void backOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
            Parent patientMainPageRoot = FXMLLoader.load(getClass().getResource("../views/patientMainPageView.fxml"));
            Scene patientMainPageScene = new Scene(patientMainPageRoot);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(patientMainPageScene);
            appStage.show();
        }
    }

