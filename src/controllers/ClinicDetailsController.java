package controllers;

import dao.OperatingDetailsDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.OperatingDetails;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ClinicDetailsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Iterator<OperatingDetails> operatingDetailsIterator = OperatingDetailsDao.findAll();
        if (operatingDetailsIterator != null) {
            while (operatingDetailsIterator.hasNext()) {
                System.out.println(operatingDetailsIterator.next());
            }
        }

    }

    @FXML
    public void backAdminMainPage(ActionEvent actionEvent) throws IOException {
        Parent adminMainPageRoot = FXMLLoader.load(getClass().getResource("../views/adminMainPageView.fxml"));
        Scene adminMainPageScene = new Scene(adminMainPageRoot);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(adminMainPageScene);
        appStage.show();
    }


}
