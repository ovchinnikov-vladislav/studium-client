package ru.kamchatgtu.studium.view.work.test;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class CreateTestWindow extends Window {

    private static Stage stageTest;

    public static Stage getStage() throws IOException {
        stageTest = new Stage();
        initStage(stageTest, "/fxml/work/test/create_test_window.fxml", 800, 600, 800, 600, "Добавление теста");
        return stageTest;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageTest = primaryStage;
        initStage(stageTest, "/fxml/work/test/create_test_window.fxml", 800, 600, 800, 600, "Добавление теста");
        stageTest.show();
    }
}
