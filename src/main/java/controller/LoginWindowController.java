package controller;

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

    @FXML private TextField loginField;
    @FXML private PasswordField passField;

    @FXML public void initialize() {

    }

    @FXML public void nextPassKey(KeyEvent event) {
        System.out.println(event.getCode());
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML public void loginClick(ActionEvent event) {
        login((Node) event.getSource(), loginField.getText(), passField.getText());
    }

    @FXML public void loginKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login((Node) event.getSource(), loginField.getText(), passField.getText());
    }

    public static void login(Node node, String login, String pass) {
        boolean isInput = Database.getRightLoginPass(login, pass, Security.userLogin);
        if (isInput) {
            try {
                Stage stage = (Stage) node.getScene().getWindow();
                Stage workStage = WorkWindow.getStage();
                workStage.show();
                System.out.println(Security.userLogin);
                stage.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    @FXML public void openRegistrationAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Stage regStage = RegistrationWindow.getStage();
        regStage.show();
        stage.close();
    }
}
