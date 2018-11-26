package view.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow extends Application {

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login_window.fxml"));
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.setTitle("ISAK");
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(LoginWindow.class.getResource("/fxml/login_window.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.setTitle("ISAK");
        stage.setResizable(false);
        stage.show();
        return stage;
    }

    public void start(String... args) {
        launch(args);
    }
}
