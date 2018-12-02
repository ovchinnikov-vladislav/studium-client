package controller;

import com.victorlaerte.asynctask.AsyncTask;
import engine.RestConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import engine.Database;
import engine.Security;
import model.User;
import view.registration.RegistrationWindow;
import view.work.WorkWindow;

public class LoginWindowController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button loginButton;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {

    }

    @FXML
    public void nextPassKey(KeyEvent event) {
        System.out.println(event.getCode());
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
        LoginTask task = new LoginTask(loginButton, progressIndicator, login, pass);
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

    protected static class LoginTask extends AsyncTask<Void, Void, Boolean> {

        private Button buttonLogin;
        private ProgressIndicator progressIndicator;
        private String login;
        private String pass;

        LoginTask(Button button, ProgressIndicator progressIndicator, String login, String pass) {
            this.buttonLogin = button;
            this.progressIndicator = progressIndicator;
            this.login = login;
            this.pass = pass;
        }

        @Override
        public void onPreExecute() {
            buttonLogin.setVisible(false);
            progressIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            User user = RestConnection.getInstance().login(login, pass);
            if (user == null)
                return false;
            Security.userLogin.setEmail(user.getEmail());
            Security.userLogin.setFio(user.getFio());
            Security.userLogin.setGroup(user.getGroup());
            Security.userLogin.setLogin(user.getLogin());
            Security.userLogin.setPhone(user.getPhone());
            Security.userLogin.setPosition(user.getPosition());
            Security.userLogin.setAccess(user.getAccess());
            Security.userLogin.setDateReg(user.getDateReg());
            Security.userLogin.setIdUser(user.getIdUser());
            Security.userLogin.setStatusPass(user.getStatusPass());
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            buttonLogin.setVisible(true);
            progressIndicator.setVisible(false);
            if (aBoolean) {
                try {
                    Stage stage = (Stage) buttonLogin.getScene().getWindow();
                    Stage workStage = WorkWindow.getStage();
                    workStage.show();
                    System.out.println(Security.userLogin);
                    stage.close();
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
