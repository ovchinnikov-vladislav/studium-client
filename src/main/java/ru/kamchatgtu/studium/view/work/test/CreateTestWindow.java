package ru.kamchatgtu.studium.view.work.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateTestWindow extends Application {

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(CreateTestWindow.class.getResource("/fxml/work/test/create_test_window.fxml"));
        int width = 800;
        int height = 600;

        Stage primaryStage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setTitle("Добавление теста");
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/work/test/create_test_window.fxml"));
        int width = 800;
        int height = 600;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);

        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setTitle("Добавление теста");
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        primaryStage.show();
    }
}
