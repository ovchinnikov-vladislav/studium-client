package ru.kamchatgtu.studium.view.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.TestWindowController;

public class TestWindow extends Application {

    public static Stage getStage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TestWindow.class.getResource("/fxml/test_window.fxml"));
        Parent root = loader.load();
        int width = 600;
        int height = 480;

        Stage primaryStage = new Stage();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setTitle("Тест");
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        primaryStage.setOnCloseRequest(((TestWindowController) loader.getController()).getCloseEventHandler());
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/test_window.fxml"));
        Parent root = loader.load();
        int width = 600;
        int height = 480;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setTitle("Тест");

        primaryStage.setOnCloseRequest(((TestWindowController) loader.getController()).getCloseEventHandler());
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        primaryStage.show();
    }
}
