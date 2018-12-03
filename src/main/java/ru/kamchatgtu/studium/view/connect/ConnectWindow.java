package ru.kamchatgtu.studium.view.connect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(ConnectWindow.class.getResource("/fxml/connect_window.fxml"));
        double width = 500;
        double height = 200;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Подключение к серверу");
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(ConnectWindow.class.getResource("/fxml/connect_window.fxml"));
        double width = 500;
        double height = 200;

        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Подключение к серверу");
        stage.setResizable(false);
        return stage;
    }

    public void start(String... args) {
        launch(args);
    }
}
