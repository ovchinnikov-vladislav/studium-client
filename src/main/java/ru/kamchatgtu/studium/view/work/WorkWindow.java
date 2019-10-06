package ru.kamchatgtu.studium.view.work;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class WorkWindow extends Window {

    private static Stage stageWork;

    public static Stage getStage() throws IOException {
        stageWork = new Stage();
        initStage(stageWork, "/fxml/work_window.fxml", 1000, 600, 1000, 600, "Studium");
        return stageWork;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stageWork = stage;
        initStage(stageWork, "/fxml/work_window.fxml", 1000, 600, 1000, 600, "Studium");
        stageWork.show();
    }
}
