package ru.kamchatgtu.studium.controller.work;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.work.TestWindow;

import java.io.IOException;

public class CreateTestPanelController {

    @FXML
    private GridPane buttonsPane;

    @FXML
    public void addTestAction(ActionEvent event) {
        try {
            Stage stage = TestWindow.getStage();
            stage.initOwner(buttonsPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
