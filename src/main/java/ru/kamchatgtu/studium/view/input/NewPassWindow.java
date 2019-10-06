package ru.kamchatgtu.studium.view.input;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class NewPassWindow extends Window {

    private static Stage stageNewPass;

    public static Stage getStage() throws IOException {
        stageNewPass = new Stage();
        initStage(stageNewPass, "/fxml/newpass_window.fxml", 300, 210, 300, 210, "Новый пароль");
        stageNewPass.setResizable(false);
        return stageNewPass;
    }

    public void start(Stage stage) throws IOException {
        stageNewPass = stage;
        initStage(stageNewPass, "/fxml/newpass_window.fxml", 300, 210, 300, 210, "Новый пароль");
        stageNewPass.setResizable(false);
        stageNewPass.show();
    }

    public void start(String... args) {
        launch(args);
    }
}
