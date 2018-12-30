package ru.kamchatgtu.studium.view.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class Message extends Alert {

    public Message(AlertType alertType) {
        super(alertType);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #f5f5f5");

    }

    public Message(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #f5f5f5");
    }


}
