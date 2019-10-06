package ru.kamchatgtu.studium.view.work.admin;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

public class SubjectDialog extends Window {

    private static Stage stageSubject;

    public static Stage getStage() throws Exception {
        stageSubject = new Stage();
        initStage(stageSubject, "/fxml/work/admin/subject_dialog.fxml", 400, 175, 400, 175, "Добавление дисциплины");
        stageSubject.setResizable(false);
        return stageSubject;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageSubject = new Stage();
        initStage(stageSubject, "/fxml/work/admin/subject_dialog.fxml", 400, 175, 400, 175, "Добавление дисциплины");
        stageSubject.setResizable(false);
        stageSubject.show();
    }
}
