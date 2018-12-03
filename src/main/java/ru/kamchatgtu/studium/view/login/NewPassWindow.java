package ru.kamchatgtu.studium.view.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPassWindow extends Application {
    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(LoginWindow.class.getResource("/fxml/newpass_window.fxml"));
        double width = 300;
        double height = 210;

        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Studium");
        stage.setResizable(false);
        stage.show();
        return stage;
    }

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/newpass_window.fxml"));
        double width = 300;
        double height = 210;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Studium");
        stage.setResizable(false);
        stage.show();
    }

    public void start(String... args) {
        launch(args);
    }
}
