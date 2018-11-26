package view.registration;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class RegistrationWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registration_window.fxml"));
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("ISAK");
        stage.setMinHeight(650);
        stage.setMinWidth(670);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(RegistrationWindow.class.getResource("/fxml/registration_window.fxml"));
        Scene scene = new Scene(root, 900, 700);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("ISAK");
        stage.setMinHeight(650);
        stage.setMinWidth(670);
        return stage;
    }

    public void start(String... args) {
        launch(args);
    }
}
