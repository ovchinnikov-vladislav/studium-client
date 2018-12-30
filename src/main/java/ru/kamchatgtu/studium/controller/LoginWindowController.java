package ru.kamchatgtu.studium.controller;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.controller.work.UsersPanelController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.login.NewPassWindow;
import ru.kamchatgtu.studium.view.registration.RegistrationWindow;
import ru.kamchatgtu.studium.view.work.WorkWindow;

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
        LoginTask task = new LoginTask(login, pass);
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

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {

        private String login;
        private String pass;

        LoginTask(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        @Override
        public void onPreExecute() {
            loginButton.setVisible(false);
            progressIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
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
            loginButton.setVisible(true);
            progressIndicator.setVisible(false);
            if (aBoolean) {
                try {
                    Stage thisStage = (Stage) loginButton.getScene().getWindow();
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
}
