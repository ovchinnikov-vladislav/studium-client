package view.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;

public class Message extends Alert {

    public Message(AlertType alertType) {
        super(alertType);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #eaf4fd");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #2292e4");


    }

    public Message(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #eaf4fd");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #2292e4");
    }


}
