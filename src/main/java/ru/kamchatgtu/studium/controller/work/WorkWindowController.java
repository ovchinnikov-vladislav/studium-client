package ru.kamchatgtu.studium.controller.work;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.kamchatgtu.studium.controller.work.admin.*;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.view.input.LoginWindow;

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
    private Button ratingStudentsMenu;
    @FXML
    private GridPane ratingStudentsPanel;
    @FXML
    private Button resultMenu;
    @FXML
    private GridPane resultsPanel;
    @FXML
    private Button testMenu;
    @FXML
    private GridPane testsPanel;
    @FXML
    private Button quesMenu;
    @FXML
    private GridPane createQuesPanel;
    @FXML
    private Button createTestMenu;
    @FXML
    private GridPane createTestPanel;
    @FXML
    private Label nameLabel;
    @FXML
    private Button adminMenu;
    @FXML
    private GridPane adminPanel;
    @FXML
    private Button systemInfoMenu;
    @FXML
    private GridPane systemInfoPanel;
    @FXML
    private Button settingsMenu;
    @FXML
    private GridPane statisticsPanel;
    @FXML
    private Button statisticsMenu;

    @FXML
    public void initialize() {
        AdminPanelController.setProgressBar(progressBar);
        CreateQuesPanelController.setProgressBar(progressBar);
        resultsPanel.setVisible(false);
        testsPanel.setVisible(false);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(false);
        adminPanel.setVisible(false);
        systemInfoPanel.setVisible(false);
        statisticsPanel.setVisible(false);
        int access = Security.USER_LOGIN.getRole().getAccess();
        if (access == 3)
            setStudent();
        else if (access == 2)
            setTeacher();
        else if (access == 1) {
            StudentsPanelController.setTaskLabel(taskLabel);
            setAdmin();
        }
        nameLabel.setText(Security.USER_LOGIN.getFio());
    }

    @FXML
    public void administratingOpen(ActionEvent event) {
        AdminPanelController.setTaskLabel(taskLabel);
        selectPanel(false,false, false, true, false, false, false, false);
    }

    @FXML
    public void systemInfoOpen(ActionEvent event) {
        selectPanel(false,false, false, false, false, false , true, false);
    }

    @FXML
    public void ratingOpen(ActionEvent event) {
        selectPanel(true,false, false, false, false, false, false, false);
    }

    @FXML
    public void resultOpen(ActionEvent event) {
        selectPanel(false, true, false, false, false, false, false, false);
    }

    @FXML
    public void testsOpen(ActionEvent event) {
        selectPanel(false,false, true, false, false, false, false, false);
    }

    @FXML
    public void createQuesOpen(ActionEvent event) {
        CreateQuesPanelController.setTaskLabel(taskLabel);
        selectPanel(false,false, false, false, true, false, false, false);
    }

    @FXML
    public void createTestOpen(ActionEvent event) {
        selectPanel(false,false, false, false, false, true, false, false);
    }

    @FXML
    public void statisticsOpen(ActionEvent event) {
        selectPanel(false,false, false, false, false, false, false, true);
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
        MenuItem exit = new MenuItem("Выйти");
        exit.setOnAction((event) -> {
            try {
                Stage stage = LoginWindow.getStage();
                stage.show();
                ((Stage) window).close();
                Security.clearUser();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(exit);
        Bounds boundsInScene = nameLabel.localToScene(nameLabel.getBoundsInLocal());
        contextMenu.show(nameLabel.getScene().getWindow(),
                window.getX() + boundsInScene.getMinX() + 50,
                window.getY() + boundsInScene.getMinY() + 50);
    }

    private void selectPanel(boolean isVisibleRatingPanel, boolean isVisibleResultsPanel, boolean isVisibleTestsPanel, boolean isVisibleAdminPanel,
                             boolean isVisibleCreateQuesPanel, boolean isVisibleCreateTestPanel, boolean isVisibleSystemInfoPanel, boolean isVisibleStatisticsPanel) {
        ratingStudentsPanel.setVisible(isVisibleRatingPanel);
        resultsPanel.setVisible(isVisibleResultsPanel);
        testsPanel.setVisible(isVisibleTestsPanel);
        adminPanel.setVisible(isVisibleAdminPanel);
        createQuesPanel.setVisible(isVisibleCreateQuesPanel);
        createTestPanel.setVisible(isVisibleCreateTestPanel);
        systemInfoPanel.setVisible(isVisibleSystemInfoPanel);
        statisticsPanel.setVisible(isVisibleStatisticsPanel);
        progressBar.setProgress(0);
    }

    private void initMenuAndPanel(boolean isVisibleRatingMenu, boolean isVisibleResultMenu, boolean isVisibleTestMenu, boolean isVisibleUsersMenu,
                                  boolean isVisibleQuesMenu, boolean isVisibleCreateTestMenu, boolean isVisibleSettingsMenu,
                                  boolean isVisibleSystemInfoMenu, boolean isVisibleStatisticsMenu) {
        if (!isVisibleRatingMenu) {
            menu.getChildren().remove(ratingStudentsMenu);
            mainPanel.getChildren().remove(ratingStudentsPanel);
        }
        if (!isVisibleResultMenu) {
            menu.getChildren().remove(resultMenu);
            mainPanel.getChildren().remove(resultsPanel);
        }
        if (!isVisibleTestMenu) {
            menu.getChildren().remove(testMenu);
            mainPanel.getChildren().remove(testsPanel);
        }
        if (!isVisibleQuesMenu) {
            menu.getChildren().remove(quesMenu);
            mainPanel.getChildren().remove(createQuesPanel);
        }
        if (!isVisibleCreateTestMenu) {
            menu.getChildren().remove(createTestMenu);
            mainPanel.getChildren().remove(createTestPanel);
        }
        if (!isVisibleUsersMenu) {
            menu.getChildren().remove(adminMenu);
            mainPanel.getChildren().remove(adminPanel);
        }
        if (!isVisibleSystemInfoMenu) {
            menu.getChildren().remove(systemInfoMenu);
            mainPanel.getChildren().remove(systemInfoPanel);
        }
        if (!isVisibleStatisticsMenu) {
            menu.getChildren().remove(statisticsMenu);
            mainPanel.getChildren().remove(statisticsPanel);
        }
        if (!isVisibleSettingsMenu) {
            menu.getChildren().remove(settingsMenu);
        }
    }

    private void setAdmin() {
        initMenuAndPanel(false, false, false, true, false, false, true, true, false);
        selectPanel(false,false, false, true, false, false, false, false);
    }

    private void setTeacher() {
        initMenuAndPanel(true,true, true, false, true, true, false, false, false);
        selectPanel(false, true, false, false, false, false, false, false);
    }

    private void setStudent() {
        initMenuAndPanel(true,true, true, false, false, false, false, false, false);
        selectPanel(false, false, true, false, false, false, false, false);
    }
}
