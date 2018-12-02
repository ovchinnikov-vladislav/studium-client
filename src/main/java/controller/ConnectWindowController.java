package controller;

import com.sun.javaws.progress.Progress;
import com.victorlaerte.asynctask.AsyncTask;
import engine.RestConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import java.io.IOException;
import engine.Security;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;
import view.login.LoginWindow;

public class ConnectWindowController {

    @FXML
    private TextField addressField;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button connectButton;

    @FXML
    public void initialize() {

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
        ConnectTask connectTask = new ConnectTask(addressField.getText(), progressIndicator, connectButton);
        connectTask.execute();
    }

    public static class ConnectTask extends AsyncTask<Void, Void, Boolean> {
        private RestConnection connection = RestConnection.getInstance();
        private ProgressIndicator indicator;
        private Button connect;
        private String url;

        ConnectTask(String url, ProgressIndicator indicator, Button connect) {
            this.url = url;
            connection.setUrl(url);
            this.indicator = indicator;
            this.connect = connect;
        }

        @Override
        public void onPreExecute() {
            connect.setVisible(false);
            indicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            return connection.connect();
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            connect.setVisible(true);
            indicator.setVisible(false);
            if (aBoolean) {
                try {
                    Security.writeFile(url);
                    Stage stageOld = (Stage) connect.getScene().getWindow();
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
