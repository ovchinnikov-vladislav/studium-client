package ru.kamchatgtu.studium.controller.work;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.view.login.LoginWindow;

import java.io.IOException;

public class WorkWindowController {

    @FXML
    private GridPane mainPanel;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label taskLabel;
    @FXML
    private GridPane menu;
    @FXML
    private Button resultMenu;
    @FXML
    private GridPane resultsPanel;
    @FXML
    private Button testMenu;
    @FXML
    private GridPane testsPanel;
    @FXML
    private Button usersMenu;
    @FXML
    private GridPane usersPanel;
    @FXML
    private Button quesMenu;
    @FXML
    private GridPane createQuesPanel;
    @FXML
    private Button createTestMenu;
    @FXML
    private GridPane createTestPanel;
    @FXML
    private Button settingsMenu;
    @FXML
    private Button systemInfoMenu;
    @FXML
    private Label nameLabel;

    @FXML
    public void initialize() {
        UsersPanelController.setProgressBar(progressBar);
        CreateQuesPanelController.setProgressBar(progressBar);
        resultsPanel.setVisible(false);
        usersPanel.setVisible(false);
        testsPanel.setVisible(false);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(false);
        int access = SecurityAES.USER_LOGIN.getGroup().getPosition().getAccess();
        if (access == 3)
            setStudent();
        else if (access == 2)
            setTeacher();
        else if (access == 1) {
            UsersPanelController.setTaskLabel(taskLabel);
            setAdmin();
        }
        nameLabel.setText(SecurityAES.USER_LOGIN.getFio());
    }

    @FXML
    public void resultOpen(ActionEvent event) {
        selectPanel(true, false, false, false, false);
    }

    @FXML
    public void testsOpen(ActionEvent event) {
        selectPanel(false, true, false, false, false);
    }

    @FXML
    public void usersOpen(ActionEvent event) {
        UsersPanelController.setTaskLabel(taskLabel);
        selectPanel(false, false, true, false, false);
    }

    @FXML
    public void createQuesOpen(ActionEvent event) {
        CreateQuesPanelController.setTaskLabel(taskLabel);
        selectPanel(false, false, false, true, false);
    }

    @FXML
    public void createTestOpen(ActionEvent event) {
        selectPanel(false, false, false, false, true);
    }

    @FXML
    public void openMenuAction(ActionEvent event) {
        initMenuLC();
    }

    @FXML
    public void openMenuMouseClicked(MouseEvent event) {
        initMenuLC();
    }

    private void initMenuLC() {
        Window window = nameLabel.getScene().getWindow();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem lc = new MenuItem("Личный кабинет");
        SeparatorMenuItem smi = new SeparatorMenuItem();
        MenuItem exit = new MenuItem("Выйти");
        exit.setOnAction((event) -> {
            try {
                Stage stage = LoginWindow.getStage();
                stage.show();
                ((Stage) window).close();
                SecurityAES.clearUser();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(lc, smi, exit);
        Bounds boundsInScene = nameLabel.localToScene(nameLabel.getBoundsInLocal());
        contextMenu.show(nameLabel.getScene().getWindow(),
                window.getX() + boundsInScene.getMinX(),
                window.getY() + boundsInScene.getMinY() + 50);
    }

    private void selectPanel(boolean isVisibleResultsPanel, boolean isVisibleTestsPanel, boolean isVisibleUsersPanel,
                             boolean isVisibleCreateQuesPanel, boolean isVisibleCreateTestPanel) {
        resultsPanel.setVisible(isVisibleResultsPanel);
        testsPanel.setVisible(isVisibleTestsPanel);
        usersPanel.setVisible(isVisibleUsersPanel);
        createQuesPanel.setVisible(isVisibleCreateQuesPanel);
        createTestPanel.setVisible(isVisibleCreateTestPanel);
        progressBar.setProgress(0);
    }

    private void initMenuAndPanel(boolean isVisibleResultMenu, boolean isVisibleTestMenu, boolean isVisibleUsersMenu,
                                  boolean isVisibleQuesMenu, boolean isVisibleCreateTestMenu, boolean isVisibleSettingsMenu,
                                  boolean isVisibleSystemInfoMenu) {
        if (!isVisibleResultMenu) {
            menu.getChildren().remove(resultMenu);
            mainPanel.getChildren().remove(resultsPanel);
        }
        if (!isVisibleTestMenu) {
            menu.getChildren().remove(testMenu);
            mainPanel.getChildren().remove(testsPanel);
        }
        if (!isVisibleUsersMenu) {
            menu.getChildren().remove(usersMenu);
            mainPanel.getChildren().remove(usersPanel);
        }
        if (!isVisibleQuesMenu) {
            menu.getChildren().remove(quesMenu);
            mainPanel.getChildren().remove(createQuesPanel);
        }
        if (!isVisibleCreateTestMenu) {
            menu.getChildren().remove(createTestMenu);
            mainPanel.getChildren().remove(createTestPanel);
        }
        if (!isVisibleSettingsMenu) {
            menu.getChildren().remove(settingsMenu);
        }
        if (!isVisibleSystemInfoMenu) {
            menu.getChildren().remove(systemInfoMenu);
        }
    }

    private void setAdmin() {
        initMenuAndPanel(false, false, true, false, false, true, true);
        selectPanel(false, false, true, false, false);
    }

    private void setTeacher() {
        initMenuAndPanel(true, true, false, true, true, false, false);
        selectPanel(true, false, false, false, false);
    }

    private void setStudent() {
        initMenuAndPanel(true, true, false, false, false, false, false);
        selectPanel(false, true, false, false, false);
    }
}
