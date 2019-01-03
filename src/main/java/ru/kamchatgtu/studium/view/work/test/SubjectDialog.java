package ru.kamchatgtu.studium.view.work.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SubjectDialog extends Application {

    public static Stage getStage() throws Exception {
        Parent root = FXMLLoader.load(SubjectDialog.class.getResource("/fxml/work/test/subject_dialog.fxml"));
        double width = 400;
        double height = 175;

        Stage primaryStage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/work/test/subject_dialog.fxml"));
        double width = 400;
        double height = 175;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Добавление дисциплины");
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        primaryStage.show();
    }
}
