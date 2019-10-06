package ru.kamchatgtu.studium.view.work.test;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class ResultTestWindow extends Window {

    private static Stage stageResultTest;

    public static Stage getStage() throws IOException {
        stageResultTest = new Stage();
        initStage(stageResultTest, "/fxml/result_test_window.fxml", 800, 680, 800, 680, "Результат");
        return stageResultTest;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageResultTest = primaryStage;
        initStage(stageResultTest, "/fxml/result_test_window.fxml", 800, 680, 800, 680, "Результат");
        stageResultTest.show();
    }
}
