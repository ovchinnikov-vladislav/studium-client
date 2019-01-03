package ru.kamchatgtu.studium.view.preloader;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreenLoader extends Preloader {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loader_window.fxml"));
        double width = 700;
        double height = 370;

        this.stage = primaryStage;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/image/icon.png"));
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        super.handleStateChangeNotification(info);
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        Preloader.ProgressNotification ppn = (Preloader.ProgressNotification) info;
        if (ppn.getProgress() == 1)
            stage.hide();
    }
}
