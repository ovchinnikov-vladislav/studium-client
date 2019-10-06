package ru.kamchatgtu.studium.controller.work;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import ru.kamchatgtu.studium.controller.work.admin.*;

public class AdminPanelController {

    private static ProgressBar progressBar;

    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane facultiesDirectionsGroupsPane,
            departmentsPane,
            subjectsPane,
            studentsPane,
            teachersPane,
            administratorsPane;
    @FXML
    private static Label taskLabel;

    @FXML
    public void initialize() {
        selectPanel(true, false, false, false, false, false);
    }

    @FXML
    public void openFacultiesDirectionsGroupsPane(Event event) {
        selectPanel(true, false, false, false, false, false);
    }

    @FXML
    public void openDepartmentsPane(Event event) {
        selectPanel(false, true, false, false, false, false);
    }

    @FXML
    public void openSubjectsPane(Event event) {
        selectPanel(false, false, true, false, false, false);
    }

    @FXML
    public void openStudentsPane(Event event) {
        selectPanel(false, false, false, true, false, false);
    }

    @FXML
    public void openTeachersPane(Event event) {
        selectPanel(false, false, false, false, true, false);
    }

    @FXML
    public void openAdministratorsPane(Event event) {
        selectPanel(false, false, false, false, false, true);
    }

    private void selectPanel(boolean isVisibleFacultiesDirectionsGroupsPane, boolean isVisibleDepartmentsPane, boolean isVisibleSubjectsPane,
                             boolean isVisibleStudentsPane, boolean isVisibleTeachersPane, boolean isVisibleAdministratorsPane) {
        if (facultiesDirectionsGroupsPane != null)
            facultiesDirectionsGroupsPane.setVisible(isVisibleFacultiesDirectionsGroupsPane);
        if (departmentsPane != null)
            departmentsPane.setVisible(isVisibleDepartmentsPane);
        if (subjectsPane != null)
            subjectsPane.setVisible(isVisibleSubjectsPane);
        if (studentsPane != null)
            studentsPane.setVisible(isVisibleStudentsPane);
        if (teachersPane != null)
            teachersPane.setVisible(isVisibleTeachersPane);
        if (administratorsPane != null)
            administratorsPane.setVisible(isVisibleAdministratorsPane);
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setProgressBar(ProgressBar progressBar) {
        AdminPanelController.progressBar = progressBar;
        FacultiesDirecitonsGroupsPanelController.setProgressBar(progressBar);
        DepartmentsPanelController.setProgressBar(progressBar);
        StudentsPanelController.setProgressBar(progressBar);
        TeachersPanelController.setProgressBar(progressBar);
        AdministratorsPanelController.setProgressBar(progressBar);
    }

    public static void setTaskLabel(Label label) {
        AdminPanelController.taskLabel = label;
        FacultiesDirecitonsGroupsPanelController.setTaskLabel(taskLabel);
        DepartmentsPanelController.setTaskLabel(taskLabel);
        StudentsPanelController.setTaskLabel(taskLabel);
        TeachersPanelController.setTaskLabel(taskLabel);
        AdministratorsPanelController.setTaskLabel(taskLabel);
    }
}
