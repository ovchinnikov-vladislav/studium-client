package ru.kamchatgtu.studium.view.work.admin;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

public class DirectionDialog extends Window {

    private static Stage stageDirection;

    public static Stage getStage() throws Exception {
        stageDirection = new Stage();
        initStage(stageDirection, "/fxml/work/admin/direction_dialog.fxml", 400, 175, 400, 175, "Добавление направления");
        stageDirection.setResizable(false);
        return stageDirection;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageDirection = primaryStage;
        initStage(stageDirection, "/fxml/work/admin/direction_dialog.fxml", 400, 175, 400, 175, "Добавление направления");
        stageDirection.setResizable(false);
        stageDirection.show();
    }
}
