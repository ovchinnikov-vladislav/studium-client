package ru.kamchatgtu.studium.engine.thread;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.Message;

public class CheckConnectTask extends AsyncTask<Void, Void, Boolean> {

    private Node node;

    public CheckConnectTask(Node node) {
        this.node = node;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Boolean doInBackground(Void... voids) {
        RestConnection restConnection = new RestConnection();
        while(restConnection.getRestUser().connect()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exc) {}
        }
        return false;
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        Message message = new Message(Alert.AlertType.ERROR);
        message.setTitle("Ошибка соединения");
        message.setHeaderText("Ошибка соединения");
        message.setContentText("Соединение прервано");
        message.showAndWait();
        System.exit(0);
    }

    @Override
    public void progressCallback(Void... voids) {

    }
}
