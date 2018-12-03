package ru.kamchatgtu.studium.controller;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.login.LoginWindow;

import java.io.IOException;

public class ConnectWindowController {

    @FXML
    private TextField addressField;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button connectButton;

    private RestConnection restConnection;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
    }

    @FXML
    public void addressFieldKeyPressed(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode()) {
            connect();
        }
    }

    @FXML
    public void connectAction(ActionEvent event) {
        connect();
    }

    private void connect() {
        ConnectTask connectTask = new ConnectTask(addressField.getText());
        connectTask.execute();
    }

    public class ConnectTask extends AsyncTask<Void, Void, Boolean> {
        private String url;

        ConnectTask(String url) {
            this.url = url;
            RestConnection.setUrl(url);
        }

        @Override
        public void onPreExecute() {
            connectButton.setVisible(false);
            progressIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            return restConnection.getRestUser().connect();
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            connectButton.setVisible(true);
            progressIndicator.setVisible(false);
            if (aBoolean) {
                try {
                    Security.writeFile(url);
                    Stage stageOld = (Stage) connectButton.getScene().getWindow();
                    Stage stageNew = LoginWindow.getStage();
                    stageNew.show();
                    stageOld.close();
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
