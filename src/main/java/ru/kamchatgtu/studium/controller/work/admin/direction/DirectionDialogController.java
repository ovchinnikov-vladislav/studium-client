package ru.kamchatgtu.studium.controller.work.admin.direction;

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
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class DirectionDialogController {

    private static boolean isAdd;
    @FXML
    public AutoSizeTextArea directionField;
    @FXML
    public HBox buttons;
    @FXML
    public Button deleteButton;
    private RestConnection rest;
    private Direction direction;

    public static void setIsAdd(boolean isAdd) {
        DirectionDialogController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        directionField.setText("");
        rest = new RestConnection();
        if (isAdd) {
            buttons.getChildren().remove(deleteButton);
            direction = new Direction();
        } else {
            direction = FacultiesDirecitonsGroupsPanelController.getSelectedDirection();
        }
        direction.setFaculty(FacultiesDirecitonsGroupsPanelController.getSelectedFaculty());
        directionField.textProperty().bindBidirectional(direction.directionNameProperty());
    }

    @FXML
    public void saveAction(ActionEvent event) {
        if (isAdd && direction != null && direction.getDirectionName() != null && direction.getDirectionName().length() > 0) {
            direction = rest.getRestDirection().add(direction);
        } else if (direction != null && direction.getDirectionName() != null && direction.getDirectionName().length() > 0) {
            direction = rest.getRestDirection().update(direction);
        }
        FacultiesDirecitonsGroupsPanelController.setSelectedDirection(direction);
        close((Node) event.getSource());
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        if (direction != null) {
            ObservableList<Group> groups = rest.getRestGroup().getGroupsByDirection(direction.getIdDirection());
            if (groups != null) {
                for (Group group : groups) {
                    rest.getRestGroup().remove(group);
                }
            }
            rest.getRestDirection().remove(direction);
        }
        FacultiesDirecitonsGroupsPanelController.setSelectedDirection(null);
        close((Node) event.getSource());
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        close((Node) event.getSource());
    }

    private void close(Node node) {
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
