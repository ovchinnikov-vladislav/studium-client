package ru.kamchatgtu.studium.engine.thread;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.input.NewPassWindow;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;

public class LoginAsync extends AsyncTask<Void, Void, Boolean> {

    private RestConnection rest;
    private Button loginButton;
    private ProgressIndicator progressIndicator;
    private Label errorLabel;
    private String login;
    private String pass;

    public LoginAsync(Button loginButton, ProgressIndicator progressIndicator, Label errorLabel, String login, String pass) {
        this.loginButton = loginButton;
        this.progressIndicator = progressIndicator;
        this.errorLabel = errorLabel;
        this.login = login;
        this.pass = pass;
        this.rest = new RestConnection();
    }

    public static void checkAccess(RestConnection restConnection) {
        int access = Security.USER_LOGIN.getRole().getAccess();
        if (access == 3) {

        } else if (access == 2) {
            //CreateQuesPanelController.setThemes(restConnection.getRestTheme().getAll());
        } else if (access == 1) {

        }
    }

    @Override
    public void onPreExecute() {
        loginButton.setVisible(false);
        progressIndicator.setVisible(true);
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        User user = rest.getRestUser().login(login, pass);
        if (user == null)
            return false;
        Security.USER_LOGIN.setUser(user);
        int access = Security.USER_LOGIN.getRole().getAccess();
        checkAccess(rest);
        return true;
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            try {
                Stage thisStage = (Stage) loginButton.getScene().getWindow();
                thisStage.hide();
                if (Security.USER_LOGIN.getStatus() != 0) {
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
        } else {
            errorLabel.setVisible(true);
        }
        loginButton.setVisible(true);
        progressIndicator.setVisible(false);
    }

    @Override
    public void progressCallback(Void... voids) {

    }
}
