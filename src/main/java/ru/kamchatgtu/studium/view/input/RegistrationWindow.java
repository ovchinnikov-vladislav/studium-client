package ru.kamchatgtu.studium.view.input;

import javafx.stage.Stage;
import ru.kamchatgtu.studium.view.Window;

import java.io.IOException;

public class RegistrationWindow extends Window {

    private static Stage stageRegistration;

    public static Stage getStage() throws IOException {
        stageRegistration = new Stage();
        initStage(stageRegistration, "/fxml/registration_window.fxml", 900, 700, 670, 650, "Регистрация");
        return stageRegistration;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stageRegistration = stage;
        initStage(stageRegistration, "/fxml/registration_window.fxml", 900, 700, 670, 650, "Регистрация");
        stage.show();
    }

    public void start(String... args) {
        launch(args);
    }
}
