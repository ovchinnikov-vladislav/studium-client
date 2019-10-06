package ru.kamchatgtu.studium.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;


public class PassWindowController {

    @FXML
    private TextField passFirstField;
    @FXML
    private TextField passSecondField;

    private RestConnection restConnection;

    @FXML
    public void nextPassKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            passSecondField.requestFocus();
        restConnection = new RestConnection();
    }

    @FXML
    public void changePassKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            change();
        }
    }

    @FXML
    public void changePassClick(MouseEvent event) {
        change();
    }

    private void change() {
        if (!passFirstField.getText().isEmpty() && !passSecondField.getText().isEmpty() && passSecondField.getText().equals(passFirstField.getText())) {
            Security.USER_LOGIN.setPassword(Security.encryptPass(passFirstField.getText()));
            Security.USER_LOGIN.setStatus(3);
            restConnection.getRestUser().update(Security.USER_LOGIN);
            try {
                WorkWindow.getStage().show();
                ((Stage) passSecondField.getScene().getWindow()).close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}
