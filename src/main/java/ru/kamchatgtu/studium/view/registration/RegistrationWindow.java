package ru.kamchatgtu.studium.view.registration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registration_window.fxml"));
        double width = 900;
        double height = 700;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Studium");
        stage.setMinHeight(650);
        stage.setMinWidth(670);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(RegistrationWindow.class.getResource("/fxml/registration_window.fxml"));
        double width = 900;
        double height = 700;

        Stage stage = new Stage();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Studium");
        stage.setMinHeight(650);
        stage.setMinWidth(670);
        return stage;
    }

    public void start(String... args) {
        launch(args);
    }
}
