package ru.kamchatgtu.studium.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.engine.thread.LoginAsync;
import ru.kamchatgtu.studium.engine.thread.RegistrationAsync;
import ru.kamchatgtu.studium.engine.thread.TestEmailAsync;
import ru.kamchatgtu.studium.engine.thread.TestLoginAsync;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.sql.Timestamp;
import java.util.Date;

public class RegistrationWindowController {

    private ObservableList<Group> groups;

    @FXML private GridPane borderLogin;
    @FXML private GridPane borderReg;
    @FXML private RowConstraints rowSecond;

    @FXML private ComboBox<Group> groupBox;
    @FXML private CheckBox groupCheckBox;
    @FXML private TextField fioRegField;
    @FXML private TextField loginRegField;
    @FXML private TextField passRegField;
    @FXML private TextField emailRegField;
    @FXML private TextField phoneField;

    @FXML private TextField loginField;
    @FXML private TextField passField;

    @FXML private Button regButton;
    @FXML private ProgressIndicator progressRegIndicator;

    @FXML private Button logButton;
    @FXML private ProgressIndicator progressLogIndicator;
    @FXML
    private Label errorLabel;

    @FXML public void initialize() {
        setGroupCheckBoxHandler();
        groups = new RestConnection().getRestGroup().getGroupsByPosition(3);
        if (groups != null)
            groupBox.getItems().addAll(groups);
        else {
            groupBox.setDisable(true);
            groupCheckBox.setSelected(true);
            groupCheckBox.setDisable(true);
        }
        loginRegField.textProperty().addListener(((observable, oldValue, newValue) -> {
            TestLoginAsync testLoginAsync = new TestLoginAsync(loginRegField);
            testLoginAsync.execute();
        }));
        emailRegField.textProperty().addListener(((observable, oldValue, newValue) -> {
            TestEmailAsync testEmailAsync = new TestEmailAsync(emailRegField);
            testEmailAsync.execute();
        }));
    }

    @FXML public void loginKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML public void passKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login(loginField.getText(), passField.getText());
    }

    @FXML public void loginAction(ActionEvent event) {
        login(loginField.getText(), passField.getText());
    }

    private void login(String login, String pass) {
        LoginAsync task = new LoginAsync(logButton, progressLogIndicator, errorLabel, login, pass);
        task.execute();
    }

    @FXML public void fioRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            loginRegField.requestFocus();
    }

    @FXML public void loginRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passRegField.requestFocus();
    }

    @FXML public void passRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            emailRegField.requestFocus();
    }

    @FXML public void emailRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            phoneField.requestFocus();
    }

    @FXML public void phoneKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            registration();
        }
    }

    @FXML public void regButtonAction(ActionEvent event) {
        registration();
    }

    private void registration() {
        User newUser = new User();
        newUser.setStatus(3);
        newUser.setFio(fioRegField.getText());
        newUser.setLogin(loginRegField.getText());
        newUser.setPassword(SecurityAES.encryptPass(passRegField.getText()));
        newUser.setEmail(emailRegField.getText());
        newUser.setPhone(phoneField.getText());
        if (!groupCheckBox.isSelected())
            newUser.setGroup(groupBox.getValue());
        else {
            Group group = new Group();
            group.setIdGroup(1);
            group.setDirection("Группа по умолчанию");
            group.setNameGroup("по умолчанию");
            newUser.setGroup(group);
        }
        Position position = new Position();
        position.setAccess(3);
        position.setIdPosition(3);
        position.setPosition("Студент");
        newUser.setPosition(position);
        newUser.setDateReg(new Timestamp(new Date().getTime()));
        newUser.setDateAuth(new Timestamp(new Date().getTime()));
        RegistrationAsync registrationAsync = new RegistrationAsync(regButton, progressRegIndicator, newUser);
        registrationAsync.execute();
    }

    private void setGroupCheckBoxHandler() {
        groupCheckBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
           if (newValue) {
               groupBox.setDisable(true);
           } else {
               groupBox.setDisable(false);
           }
        });
    }

    @FXML public void loginMenuClick(ActionEvent event) {
        borderLogin.setVisible(true);
        borderReg.setVisible(false);
    }

    @FXML public void regMenuClick(ActionEvent event) {
        borderReg.setVisible(true);
        borderLogin.setVisible(false);
    }

}
