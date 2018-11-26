package controller;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import javafx.stage.Stage;
import javafx.util.Duration;
import engine.Database;
import engine.Security;
import view.login.LoginWindow;
import view.message.Message;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ConnectWindowController {

    @FXML private TextField addressField;
    @FXML private TextField dbField;
    @FXML private TextField loginField;
    @FXML private PasswordField passField;

    @FXML public void initialize() {

    }

    @FXML public void addressFieldKeyPressed(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode())
            dbField.requestFocus();
    }

    @FXML public void dbFieldKeyPressed(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode())
            loginField.requestFocus();
    }

    @FXML public void loginFieldKeyPressed(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode())
            passField.requestFocus();
    }

    @FXML public void passFieldKeyPressed(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode()) {
            try {
                connect(event);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    @FXML public void connectAction(ActionEvent event) {
        try {
            connect(event);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    private void connect(Event event) throws IOException {
        String[] address = addressField.getText().split(":");
        String db = dbField.getText();
        String login = loginField.getText();
        String pass = passField.getText();
        if (address.length == 2) {
            Database.setUrl("jdbc:mysql://" + address[0] + ":" + address[1] + "/" + db);
            Security.writeFile(address[0], address[1], db, login, pass);
        }
        else if (address.length == 1) {
            Database.setUrl("jdbc:mysql://" + address[0] + ":3306/" + db);

        }
        Database.setUser(login);
        Database.setPassword(pass);
        TestConnection testConnection = new TestConnection();
        FutureTask<Boolean> result = new FutureTask<>(testConnection);
        Thread thread = new Thread(result);
        thread.start();
        final Alert alert = new Message(Alert.AlertType.INFORMATION);
        alert.setTitle("Подключение к БД");
        alert.setHeaderText(null);
        alert.setContentText("Ожидание подключения.");
        alert.show();
        boolean flag = false;
        try {
            flag = result.get();
        } catch (ExecutionException | InterruptedException exc) {
            exc.printStackTrace();
        }
        alert.close();
        if (!flag) {
            Alert alertE = new Message(Alert.AlertType.ERROR);
            alertE.setTitle("Подключение к БД");
            alertE.setHeaderText("Ошибка соединения с БД");
            alertE.setContentText("Проверьте данные соединения или обратитесь к системному администратору.");
            DialogPane dialogPane = alert.getDialogPane();
            alertE.showAndWait();
        } else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Stage loginWindow = LoginWindow.getStage();
            writeDBdata(address, db, login, pass);
            loginWindow.show();
            stage.close();
        }
    }

    private void writeDBdata(String[] address, String db, String login, String pass) throws IOException {
        if (address.length == 2)
            Security.writeFile(address[0], address[1], db, login, pass);
        else if (address.length == 1)
            Security.writeFile(address[0], "3306", db, login, pass);
    }

    public static class TestConnection implements Callable<Boolean> {
        @Override
        public Boolean call() {
            try {
                Database.testConnection();
                return true;
            } catch (SQLException exc) {
                return false;
            }
        }
    }
}
