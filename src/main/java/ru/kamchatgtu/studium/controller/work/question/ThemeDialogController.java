package ru.kamchatgtu.studium.controller.work.question;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class ThemeDialogController {

    private static boolean isAdd;
    @FXML
    public TextField themeField;
    @FXML
    public HBox buttons;
    @FXML
    public Button deleteButton;
    private RestConnection rest;
    private Theme theme;

    public static void setIsAdd(boolean isAdd) {
        ThemeDialogController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        themeField.setText("");
        rest = new RestConnection();
        if (isAdd) {
            buttons.getChildren().remove(deleteButton);
            theme = new Theme();
        } else {
            theme = CreateQuesPanelController.getSelectedTheme();
        }
        themeField.textProperty().bindBidirectional(theme.themeProperty());
    }

    @FXML
    public void saveAction(ActionEvent event) {
        if (isAdd && theme != null && theme.getTheme() != null && theme.getTheme().length() > 0) {
            theme = rest.getRestTheme().add(theme);
        } else if (theme != null && theme.getTheme() != null && theme.getTheme().length() > 0) {
            theme = rest.getRestTheme().update(theme);
        }
        CreateQuesPanelController.setSelectedTheme(theme);
        close((Node) event.getSource());
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        if (theme != null) {
            rest.getRestTheme().remove(theme);
        }
        CreateQuesPanelController.setSelectedTheme(null);
        close((Node) event.getSource());
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        close((Node) event.getSource());
    }

    private void close(Node node) {
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
