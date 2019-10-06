package ru.kamchatgtu.studium.controller.work.admin.faculty;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.component.AutoSizeTextArea;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.controller.work.admin.FacultiesDirecitonsGroupsPanelController;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class FacultyDialogController {

    private static boolean isAdd;
    @FXML
    public AutoSizeTextArea facultyField;
    @FXML
    public HBox buttons;
    @FXML
    public Button deleteButton;
    private RestConnection rest;
    private Faculty faculty;

    public static void setIsAdd(boolean isAdd) {
        FacultyDialogController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        facultyField.setText("");
        rest = new RestConnection();
        if (isAdd) {
            buttons.getChildren().remove(deleteButton);
            faculty = new Faculty();
        } else {
            faculty = FacultiesDirecitonsGroupsPanelController.getSelectedFaculty();
        }
        facultyField.textProperty().bindBidirectional(faculty.facultyNameProperty());
    }

    @FXML
    public void saveAction(ActionEvent event) {
        if (isAdd && faculty != null && faculty.getFacultyName() != null && faculty.getFacultyName().length() > 0) {
            faculty = rest.getRestFaculty().add(faculty);
        } else if (faculty != null && faculty.getFacultyName() != null && faculty.getFacultyName().length() > 0) {
            faculty = rest.getRestFaculty().update(faculty);
        }
        FacultiesDirecitonsGroupsPanelController.setSelectedFaculty(faculty);
        close((Node) event.getSource());
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        if (faculty != null) {
            ObservableList<Direction> directions = rest.getRestDirection().getByFaculty(faculty.getIdFaculty());
            if (directions != null) {
                for (Direction direction : directions) {
                    ObservableList<Group> groups = rest.getRestGroup().getGroupsByDirection(direction.getIdDirection());
                    if (groups != null) {
                        for (Group group : groups)
                            rest.getRestGroup().remove(group);
                    }
                    rest.getRestDirection().remove(direction);
                }
            }
            rest.getRestFaculty().remove(faculty);
        }
        FacultiesDirecitonsGroupsPanelController.setSelectedFaculty(null);
        close((Node) event.getSource());
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        FacultiesDirecitonsGroupsPanelController.setSelectedFaculty(null);
        close((Node) event.getSource());
    }

    private void close(Node node) {
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
