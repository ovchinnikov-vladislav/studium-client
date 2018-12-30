package ru.kamchatgtu.studium.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.engine.SecurityAES;

public class WorkWindowController {

    @FXML
    GridPane mainPanel;

    @FXML
    private ProgressBar progressBar;
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
    public void initialize() {
        int access = SecurityAES.USER_LOGIN.getPosition().getAccess();
        if (access == 3)
            setStudent();
        else if (access == 2)
            setTeacher();
        else if (access == 1)
            setAdmin();
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
        selectPanel(false, false, true, false, false);
    }

    @FXML
    public void createQuesOpen(ActionEvent event) {
        selectPanel(false, false, false, true, false);
    }

    @FXML
    public void createTestOpen(ActionEvent event) {
        selectPanel(false, false, false, false, true);
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
                                  boolean isVisibleQuesMenu, boolean isVisibleCreateTestMenu, boolean isVisibleSettingsMenu) {
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
    }

    private void setAdmin() {
        initMenuAndPanel(false, false, true, false, false, true);
        selectPanel(false, false, true, false, false);
    }

    private void setTeacher() {
        initMenuAndPanel(true, true, false, true, true, false);
        selectPanel(true, false, false, false, false);
    }

    private void setStudent() {
        initMenuAndPanel(true, true, false, false, false, false);
        selectPanel(false, true, false, false, false);
    }
}
