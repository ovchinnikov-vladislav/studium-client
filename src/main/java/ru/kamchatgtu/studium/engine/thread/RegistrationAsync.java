package ru.kamchatgtu.studium.engine.thread;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.controller.work.UsersPanelController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;

public class RegistrationAsync extends AsyncTask<Void, Void, Boolean> {

    private Button regButton;
    private ProgressIndicator progressIndicator;
    private RestConnection rest;
    private User user;

    public RegistrationAsync(Button regButton, ProgressIndicator progressIndicator, User user) {
        this.user = user;
        this.regButton = regButton;
        this.progressIndicator = progressIndicator;
        this.rest = new RestConnection();
    }

    @Override
    public void onPreExecute() {
        regButton.setVisible(false);
        progressIndicator.setVisible(true);
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        User newUser = rest.getRestUser().add(user);
        UsersPanelController.setUsers(rest.getRestUser().getAll());
        UsersPanelController.setRoles(rest.getRestRole().getAll());
        CreateQuesPanelController.setThemes(rest.getRestTheme().getAll());
        SecurityAES.USER_LOGIN.setUser(user);
        LoginAsync.checkAccess(rest);
        return newUser != null;
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        regButton.setVisible(true);
        progressIndicator.setVisible(false);
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
