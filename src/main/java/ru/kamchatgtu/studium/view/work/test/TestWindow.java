package ru.kamchatgtu.studium.view.work.test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.TestWindowController;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class TestWindow extends Window {

    private static Stage stageTest;

    public static Stage getStage() throws IOException {
        stageTest = new Stage();
        initStage();
        return stageTest;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageTest = primaryStage;
        initStage();
        stageTest.show();
    }

    private static void initStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TestWindow.class.getResource("/fxml/test_window.fxml"));
        Parent root = loader.load();
        int width = 800;
        int height = 680;

        initScene(stageTest, root, width, height);
        stageTest.setMinHeight(height);
        stageTest.setMinWidth(width);
        stageTest.setTitle("Тест");
        stageTest.getIcons().add(new Image("/image/icon.png"));
        stageTest.setOnCloseRequest(((TestWindowController) loader.getController()).getCloseEventHandler());
    }
}
