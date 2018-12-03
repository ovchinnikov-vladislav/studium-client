package ru.kamchatgtu.studium.controller.work;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class WorkWindowController {

    @FXML
    private ProgressBar progressBar;

    //-----------------Меню---------------------
    @FXML
    private GridPane resultsPanel;
    @FXML
    private GridPane testsPanel;
    @FXML
    private GridPane usersPanel;
    @FXML
    private GridPane createQuesPanel;
    @FXML
    private GridPane createTestPanel;

    @FXML
    public void resultOpen(ActionEvent event) {
        resultsPanel.setVisible(true);
        testsPanel.setVisible(false);
        usersPanel.setVisible(false);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(false);
        progressBar.setProgress(0);
    }

    @FXML
    public void testsOpen(ActionEvent event) {
        resultsPanel.setVisible(false);
        testsPanel.setVisible(true);
        usersPanel.setVisible(false);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(false);
        progressBar.setProgress(0);
    }

    @FXML
    public void usersOpen(ActionEvent event) {
        resultsPanel.setVisible(false);
        testsPanel.setVisible(false);
        usersPanel.setVisible(true);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(false);
        progressBar.setProgress(0);
    }

    @FXML
    public void createQuesOpen(ActionEvent event) {
        resultsPanel.setVisible(false);
        testsPanel.setVisible(false);
        usersPanel.setVisible(false);
        createQuesPanel.setVisible(true);
        createTestPanel.setVisible(false);
        progressBar.setProgress(0);
    }

    @FXML
    public void createTestOpen(ActionEvent event) {
        resultsPanel.setVisible(false);
        testsPanel.setVisible(false);
        usersPanel.setVisible(false);
        createQuesPanel.setVisible(false);
        createTestPanel.setVisible(true);
        progressBar.setProgress(0);
    }
    //------------------------------------------------
}
