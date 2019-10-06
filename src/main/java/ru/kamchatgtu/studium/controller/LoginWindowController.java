package ru.kamchatgtu.studium.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.component.TextFieldTooltipPattern;
import ru.kamchatgtu.studium.engine.thread.LoginAsync;
import ru.kamchatgtu.studium.view.input.RegistrationWindow;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginWindowController {

    private Stage stage;

    @FXML
    private GridPane loginPane;
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

    private Tooltip tooltipLogin, tooltipPass;

    @FXML
    public void initialize() {
        initLoginField();
        initPassField();
        initStage(tooltipLogin, tooltipPass);
    }

    private void initLoginField() {
        Pattern p = Pattern.compile("[A-z0-9]{0,16}");
        tooltipLogin = new Tooltip("Логин может состоять только из символов латинского\nалфавита и цифр (максимальная длина 16)");
        TextFieldTooltipPattern.initField(loginField, tooltipLogin, p);
    }

    private void initPassField() {
        Pattern p = Pattern.compile("[A-zА-я0-9]");
        tooltipPass = new Tooltip("Пароль должен содержать буквы,\nцифры и специальные символы");
        TextFieldTooltipPattern.initField(passField, tooltipPass, p);
    }

    private void initStage(Tooltip... tooltip) {
        loginPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == null && newValue != null) {
                newValue.windowProperty().addListener((observable1, oldValue1, newValue1) -> {
                    stage = (Stage) newValue1;
                    stage.xProperty().addListener(observable2 -> {
                        for (Tooltip t : tooltip)
                            t.hide();
                    });
                    stage.yProperty().addListener(observable2 -> {
                        for (Tooltip t : tooltip)
                            t.hide();
                    });
                });
            }
        });
    }

    @FXML
    public void nextPassKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML
    public void loginClick(ActionEvent event) {
        System.out.println(event);
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
