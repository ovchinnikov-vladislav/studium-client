package ru.kamchatgtu.studium.controller.work;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class TestsPanelController {

    private RestConnection restConnection;
    //-------------Список тестов-------------------
    // Компоненты раздела "Список тестов"
    @FXML
    private GridPane testsPane;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        initTests();
    }

    // Методы раздела "Список тестов"
    private void initTests() {
        ObservableList<Subject> subjects = restConnection.getRestSubject().getAll();
        int rowSubject = 0;
        for (Subject subject : subjects) {
            ObservableList<Test> tests = restConnection.getRestTest().getTestsBySubject(subject.getIdSubject());
            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().add("borderTest");
            Label label = new Label(subject.getNameSubject());
            GridPane.setMargin(label, new Insets(5));
            Separator separator = new Separator();
            GridPane.setHalignment(separator, HPos.CENTER);
            int rowTest = 0;
            gridPane.add(label, 0, rowTest++);
            gridPane.add(separator, 0, rowTest++);
            for (Test test : tests) {
                Hyperlink hyperlink = new Hyperlink(test.getNameTest());
                gridPane.add(hyperlink, 0, rowTest++);
            }
            testsPane.add(gridPane, 0, rowSubject++);
        }
    }
    //----------------------------------------------
}
