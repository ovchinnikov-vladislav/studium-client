package view.work;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;

public class WorkWindow extends Application {

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/work_windows.fxml"));
        Scene scene = new Scene(root,1000, 600);
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(550);
        stage.setTitle("IKAS");
        stage.show();
    }

    public static Stage getStage() throws IOException {
        Parent root = FXMLLoader.load(WorkWindow.class.getResource("/fxml/work_windows.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root,1000, 600);
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(550);
        stage.setTitle("IKAS");
        stage.show();
        return stage;
    }
}
