package ru.kamchatgtu.studium.view.work;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class WorkWindow extends Application {

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(WorkWindow.class.getResource("/fxml/work/work_window.fxml"));
        double width = 1000;
        double height = 600;

        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root,width, height);
        stage.setScene(scene);
        stage.setMinWidth(950);
        stage.setMinHeight(600);
        stage.setTitle("Studium");
        stage.getIcons().add(new Image("/image/atom-science-symbol-16.png"));
        stage.show();
        return stage;
    }

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/work/work_window.fxml"));
        double width = 1000;
        double height = 600;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setMinWidth(950);
        stage.setMinHeight(600);
        stage.setTitle("Studium");
        stage.getIcons().add(new Image("/image/atom-science-symbol-16.png"));
        stage.show();
    }
}
