package ru.kamchatgtu.studium.view.input;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class ConnectWindow extends Window {

    private static Stage stageConnect;

    public static Stage getStage() throws IOException {
        stageConnect = new Stage();
        initStage(stageConnect, "/fxml/connect_window.fxml", 500, 200, 500, 200, "Подключение к серверу");
        stageConnect.setResizable(false);
        return stageConnect;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stageConnect = stage;
        initStage(stageConnect, "/fxml/connect_window.fxml", 500, 200, 500, 200, "Подключение к серверу");
        stageConnect.setResizable(false);
        stageConnect.show();
    }

    public void start(String... args) {
        launch(args);
    }
}
