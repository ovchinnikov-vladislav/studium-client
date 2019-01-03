package ru.kamchatgtu.studium.view.work.question;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class QuestionDialog extends Application {

    public static Stage getStage() throws Exception {
        Parent root = FXMLLoader.load(QuestionDialog.class.getResource("/fxml/work/question/question_dialog.fxml"));
        double width = 450;
        double height = 300;

        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/image/icon.png"));
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/work/question/question_dialog.fxml"));
        double width = 450;
        double height = 300;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Добавление вопроса");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        primaryStage.show();
    }
}
