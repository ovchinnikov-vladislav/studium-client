package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import engine.Database;
import engine.Security;
import view.work.WorkWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RegistrationWindowController {

    @FXML private GridPane borderLogin;
    @FXML private GridPane borderReg;
    @FXML private RowConstraints rowSecond;

    @FXML private ComboBox<String> groupBox;
    @FXML private CheckBox groupCheckBox;
    @FXML private TextField fioRegField;
    @FXML private TextField loginRegField;
    @FXML private TextField passRegField;
    @FXML private TextField emailRegField;
    @FXML private TextField phoneField;

    @FXML private TextField loginField;
    @FXML private TextField passField;

    @FXML public void initialize() {
        setGroupCheckBoxHandler();
        groupBox.getItems().addAll(Database.getGroupsStudent());
    }

    @FXML public void loginKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML public void passKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            LoginWindowController.login(passField, loginField.getText(), passField.getText());
    }

    @FXML public void loginAction(ActionEvent event) {
        LoginWindowController.login(passField, loginField.getText(), passField.getText());
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
        boolean isExecute = Database.addUserGroup(3, groupBox.getValue(), "", fioRegField.getText(), loginRegField.getText(),
                Security.getMd5(passRegField.getText()), phoneField.getText(), emailRegField.getText(), "студент");
        if (!isExecute) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Не удалось зарегистироваться");
            alert.setContentText("Проверьте верные ли данные.");
            alert.showAndWait();
        } else
            LoginWindowController.login(loginRegField, loginRegField.getText(), passRegField.getText());
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
        rowSecond.setPrefHeight(245);
        borderLogin.setVisible(true);
        borderReg.setVisible(false);
    }

    @FXML public void regMenuClick(ActionEvent event) {
        rowSecond.setPrefHeight(482);
        borderReg.setVisible(true);
        borderLogin.setVisible(false);
    }
}
