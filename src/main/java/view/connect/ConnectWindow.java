package view.connect;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class ConnectWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(ConnectWindow.class.getResource("/fxml/connect_window.fxml"));
        Scene scene = new Scene(root, 300, 350);
        stage.setScene(scene);
        stage.setTitle("Подключение к БД");
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(ConnectWindow.class.getResource("/fxml/connect_window.fxml"));
        Scene scene = new Scene(root, 300, 350);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Подключение к БД");
        stage.setResizable(false);
        return stage;
    }

    public void start(String... args) {
        launch(args);
    }
}
