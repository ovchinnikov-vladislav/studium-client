package ru.kamchatgtu.studium.controller;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.controller.work.UsersPanelController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.engine.thread.ErrorTextFieldAsync;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.login.NewPassWindow;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;
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

    private RestConnection restConnection;

    @FXML public void initialize() {
        restConnection = new RestConnection();
        setGroupCheckBoxHandler();
        groups = restConnection.getRestGroup().getGroupsByPosition(3);
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
        LoginTask task = new LoginTask(login, pass);
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
        RegistrationAsync registrationAsync = new RegistrationAsync(newUser);
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

    private class TestLoginAsync extends ErrorTextFieldAsync {

        TestLoginAsync(TextField loginField) {
            super(loginField);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            User user = restConnection.getRestUser().getUserByLogin(getErrorText().getText());
            return user != null;
        }
    }

    private class TestEmailAsync extends ErrorTextFieldAsync {

        TestEmailAsync(TextField emailField) {
            super(emailField);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            User user = restConnection.getRestUser().getUserByEmail(getErrorText().getText());
            return user != null;
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {

        private String login;
        private String pass;

        LoginTask(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        @Override
        public void onPreExecute() {
            logButton.setVisible(false);
            progressLogIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            UsersPanelController.setUsers(restConnection.getRestUser().getAll());
            UsersPanelController.setPositions(restConnection.getRestPosition().getAll());
            CreateQuesPanelController.setThemes(restConnection.getRestTheme().getAll());
            User user = restConnection.getRestUser().login(login, pass);
            if (user == null)
                return false;
            SecurityAES.USER_LOGIN.setUser(user);
            int access = SecurityAES.USER_LOGIN.getPosition().getAccess();
            if (access == 3) {

            } else if (access == 2) {
                CreateQuesPanelController.setThemes(restConnection.getRestTheme().getAll());
            } else if (access == 1) {
                UsersPanelController.setUsers(restConnection.getRestUser().getAll());
                UsersPanelController.setPositions(restConnection.getRestPosition().getAll());
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            logButton.setVisible(true);
            progressLogIndicator.setVisible(false);
            if (aBoolean) {
                try {
                    Stage thisStage = (Stage) logButton.getScene().getWindow();
                    if (SecurityAES.USER_LOGIN.getStatus() != 0) {
                        Stage workStage = WorkWindow.getStage();
                        workStage.show();
                    } else {
                        Stage passStage = NewPassWindow.getStage();
                        passStage.show();
                    }
                    thisStage.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class RegistrationAsync extends AsyncTask<Void, Void, Boolean> {

        private User user;

        RegistrationAsync(User user) {
            this.user = user;
        }

        @Override
        public void onPreExecute() {
            regButton.setVisible(false);
            progressRegIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            User newUser = restConnection.getRestUser().add(user);
            UsersPanelController.setUsers(restConnection.getRestUser().getAll());
            UsersPanelController.setPositions(restConnection.getRestPosition().getAll());
            CreateQuesPanelController.setThemes(restConnection.getRestTheme().getAll());
            SecurityAES.USER_LOGIN.setUser(user);
            int access = SecurityAES.USER_LOGIN.getPosition().getAccess();
            if (access == 3) {

            } else if (access == 2) {
                CreateQuesPanelController.setThemes(restConnection.getRestTheme().getAll());
            } else if (access == 1) {
                UsersPanelController.setUsers(restConnection.getRestUser().getAll());
                UsersPanelController.setPositions(restConnection.getRestPosition().getAll());
            }
            return newUser != null;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            regButton.setVisible(true);
            progressRegIndicator.setVisible(false);
            if (aBoolean) {
                try {
                    Stage thisStage = (Stage) regButton.getScene().getWindow();
                    Stage workStage = WorkWindow.getStage();
                    workStage.show();
                    thisStage.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
