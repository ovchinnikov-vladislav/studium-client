package ru.kamchatgtu.studium.view.input;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class LoginWindow extends Window {

    private static Stage stageLogin;

    public static Stage getStage() throws IOException {
        stageLogin = new Stage();
        initStage(stageLogin, "/fxml/login_window.fxml", 300, 250, 300, 250, "Вход");
        stageLogin.setResizable(false);
        return stageLogin;
    }

    public void start(Stage stage) throws IOException {
        stageLogin = stage;
        initStage(stageLogin, "/fxml/login_window.fxml", 300, 250, 300, 250, "Вход");
        stageLogin.setResizable(false);
        stageLogin.show();
    }

    public void start(String... args) {
        launch(args);
    }
}
