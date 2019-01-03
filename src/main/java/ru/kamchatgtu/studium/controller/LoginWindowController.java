package ru.kamchatgtu.studium.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.thread.LoginAsync;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.registration.RegistrationWindow;

import java.io.IOException;

public class LoginWindowController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button loginButton;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label errorLabel;

    private RestConnection restConnection;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
    }

    @FXML
    public void nextPassKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML
    public void loginClick(ActionEvent event) {
        login(loginField.getText(), passField.getText());
    }

    @FXML
    public void loginKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login(loginField.getText(), passField.getText());
    }

    private void login(String login, String pass) {
        LoginAsync task = new LoginAsync(loginButton, progressIndicator, errorLabel, login, pass);
        task.execute();
    }

    @FXML
    public void openRegistrationAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Stage regStage = RegistrationWindow.getStage();
        regStage.show();
        stage.close();
    }

}
