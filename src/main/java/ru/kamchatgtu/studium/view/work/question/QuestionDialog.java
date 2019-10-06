package ru.kamchatgtu.studium.view.work.question;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

public class QuestionDialog extends Window {

    private static Stage stageQuestion;

    public static Stage getStage() throws Exception {
        stageQuestion = new Stage();
        initStage(stageQuestion, "/fxml/work/question/question_dialog.fxml", 450, 300, 450, 300, "Добавление вопроса");
        stageQuestion.setResizable(false);
        return stageQuestion;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageQuestion = primaryStage;
        initStage(stageQuestion, "/fxml/work/question/question_dialog.fxml", 450, 300, 450, 300, "Добавление вопроса");
        stageQuestion.setResizable(false);
        stageQuestion.show();
    }
}
