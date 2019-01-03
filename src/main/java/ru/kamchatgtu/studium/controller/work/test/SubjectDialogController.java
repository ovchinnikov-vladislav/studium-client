package ru.kamchatgtu.studium.controller.work.test;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class SubjectDialogController {

    private static boolean isAdd;
    @FXML
    public TextField subjectField;
    @FXML
    public HBox buttons;
    @FXML
    public Button deleteButton;

    private RestConnection rest;
    private Subject subject;

    public static void setIsAdd(boolean isAdd) {
        SubjectDialogController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        subjectField.setText("");
        rest = new RestConnection();
        if (isAdd) {
            buttons.getChildren().remove(deleteButton);
            subject = new Subject();
        } else {
            subject = CreateTestWindowController.getSelectedSubject();
        }
        subjectField.textProperty().bindBidirectional(subject.nameSubjectProperty());
    }

    @FXML
    public void saveAction(ActionEvent event) {
        if (isAdd && subject != null && subject.getNameSubject() != null && subject.getNameSubject().length() > 0) {
            subject = rest.getRestSubject().add(subject);
        } else if (subject != null && subject.getNameSubject() != null && subject.getNameSubject().length() > 0) {
            subject = rest.getRestSubject().update(subject);
        }
        CreateTestWindowController.setSelectedSubject(subject);
        close((Node) event.getSource());
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        if (subject != null) {
            ObservableList<Test> tests = rest.getRestTest().getTestsBySubject(subject.getIdSubject());
            for (Test test : tests)
                rest.getRestTest().remove(test);
            rest.getRestSubject().remove(subject);
        }
        CreateTestWindowController.setSelectedSubject(null);
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
