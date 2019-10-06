package ru.kamchatgtu.studium.view.work.admin;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

public class FacultyDialog extends Window {

    private static Stage stageFaculty;

    public static Stage getStage() throws Exception {
        stageFaculty = new Stage();
        initStage(stageFaculty, "/fxml/work/admin/faculty_dialog.fxml", 400, 175, 400, 175, "Добавление факультета");
        stageFaculty.setResizable(false);
        return stageFaculty;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageFaculty = primaryStage;
        initStage(stageFaculty, "/fxml/work/admin/faculty_dialog.fxml", 400, 175, 400, 175, "Добавление факультета");
        stageFaculty.setResizable(false);
        stageFaculty.show();
    }
}
