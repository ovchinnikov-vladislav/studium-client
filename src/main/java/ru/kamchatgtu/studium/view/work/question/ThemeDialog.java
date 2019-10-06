package ru.kamchatgtu.studium.view.work.question;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

public class ThemeDialog extends Window {

    private static Stage stageTheme;

    public static Stage getStage() throws Exception {
        stageTheme = new Stage();
        initStage(stageTheme, "/fxml/work/question/theme_dialog.fxml", 400, 175, 400, 175, "Добавление темы");
        stageTheme.setResizable(false);
        return stageTheme;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageTheme = primaryStage;
        initStage(stageTheme, "/fxml/work/question/theme_dialog.fxml", 400, 175, 400, 175, "Добавление темы");
        stageTheme.setResizable(false);
        stageTheme.show();
    }
}
