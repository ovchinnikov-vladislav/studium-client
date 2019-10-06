package ru.kamchatgtu.studium.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;

public abstract class Window extends Application {

    protected static void initStage(Stage stage, String resources, double width, double height, double minWidth, double minHeight, String title) throws IOException {
        Parent root = FXMLLoader.load(WorkWindow.class.getResource(resources));
        initScene(stage, root, width, height);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setTitle(title);
        stage.getIcons().add(new Image("/image/icon.png"));
    }

    protected static void initScene(Stage stage, Parent root, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root,width, height);
        stage.setScene(scene);
    }
}
