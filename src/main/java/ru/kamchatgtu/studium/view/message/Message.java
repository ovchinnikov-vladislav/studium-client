package ru.kamchatgtu.studium.view.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Message extends Alert {

    public Message(AlertType alertType) {
        super(alertType);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #f5f5f5");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(
                new Image(this.getClass().getResource("/image/icon.png").toString()));
    }

    public Message(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #f5f5f5");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(
                new Image(this.getClass().getResource("/image/icon.png").toString()));
    }


}
