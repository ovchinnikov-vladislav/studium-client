package ru.kamchatgtu.studium.controller.work.test;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Date;

public class CreateTestWindowController {

    private static RestConnection rest;
    private static ObservableList<Subject> subjects;
    private static Test selectedTest;
    private Subject selectedSubject;
    private static boolean isAdd = false;
    private ObservableList<Question> questions;
    @FXML
    private GridPane mainPanel;

    @FXML
    private TextField nameTestField;

    @FXML
    private ComboBox<String> hoursBox;
    @FXML
    private ComboBox<String> minutesBox;
    @FXML
    private ComboBox<String> secondsBox;

    @FXML
    private ComboBox<Subject> subjectsBox;

    @FXML
    private TextField searchResultsTextField;
    @FXML
    private TableView<Question> questionsTable;
    @FXML
    private TableColumn<Question, String> idQuestionColumn;
    @FXML
    private TableColumn<Question, String> themeColumn;
    @FXML
    private TableColumn<Question, String> typeColumn;
    @FXML
    private TableColumn<Question, Boolean> checkQuestionColumn;


    public static Test getSelectedTest() {
        return selectedTest;
    }

    public static void setSelectedTest(Test selectedTest) {
        CreateTestWindowController.selectedTest = selectedTest;
    }

    public static boolean isIsAdd() {
        return isAdd;
    }

    public static void setIsAdd(boolean isAdd) {
        CreateTestWindowController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        initTimeBox();
        rest = new RestConnection();
        subjects = rest.getRestSubject().getByUser(Security.USER_LOGIN.getIdUser());
        subjectsBox.setItems(subjects);
        initSubjectBox();
        questions = rest.getRestQuestion().getAll();
        questionsTable.setItems(questions);
        initIdQuestionColumn();
        initThemeColumn();
        initTypeColumn();
        initCheckQuestionColumn();
        initSearchQuestions();
        if (selectedTest != null) {
            initUpdateTest();
        }
    }

    @FXML
    public void saveAction() {
        if (isIsAdd()) {
            Test test = new Test();
            test.setDateEdit(new Date());
            selectedTest = createTest(test);
            rest.getRestTest().add(selectedTest);
        } else {
            selectedTest = createTest(selectedTest);
            rest.getRestTest().update(selectedTest);
        }
        ((Stage) nameTestField.getScene().getWindow()).close();
    }

    private Test createTest(Test test) {
       /* selectedSubject.getDirections().removeAll(selectedSubject.getDirections());
        for (int i = 0; i < directionsTable.getItems().size(); i++) {
            Direction direction = directionsTable.getItems().get(i);
            if (direction.isInSubject())
                selectedSubject.getDirections().add(direction);
        }
        Subject subject = rest.getRestSubject().update(selectedSubject);*/
        if (selectedTest != null)
            selectedTest.getQuestions().removeAll(selectedTest.getQuestions());
        for (int i = 0; i < questionsTable.getItems().size(); i++) {
            Question question = questionsTable.getItems().get(i);
            if (question.isInTest())
                test.getQuestions().add(question);
        }
        test.setTestName(nameTestField.getText());
        test.setSubject(selectedSubject);
        Date date = new Date();
        date.setTime(0);
        date.setHours(Integer.parseInt(hoursBox.getValue()));
        date.setMinutes(Integer.parseInt(minutesBox.getValue()));
        date.setSeconds(Integer.parseInt(secondsBox.getValue()));
        test.setTimer(date);
        test.setUser(Security.USER_LOGIN);
        return test;
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        if (!isIsAdd())
            rest.getRestTest().remove(selectedTest);
        ((Stage) nameTestField.getScene().getWindow()).close();
    }

    private void initTimeBox() {
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        ObservableList<String> seconds = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            if (i < 24) {
                if (i < 10) {
                    hours.add("0" + i);
                    minutes.add("0" + i);
                    seconds.add("0" + i);
                } else {
                    hours.add(i + "");
                    minutes.add(i + "");
                    seconds.add(i + "");
                }
            } else {
                minutes.add(i + "");
                seconds.add(i + "");
            }
        }
        hoursBox.setItems(hours);
        hoursBox.getSelectionModel().select(0);
        minutesBox.setItems(minutes);
        minutesBox.getSelectionModel().select(0);
        secondsBox.setItems(seconds);
        secondsBox.getSelectionModel().select(0);
    }

    private void initSubjectBox() {
        subjectsBox.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (subjectsBox.getSelectionModel().getSelectedIndex() != -1) {
                selectedSubject = subjects.get(subjectsBox.getSelectionModel().getSelectedIndex());
            }
        }));
    }

    private void initIdQuestionColumn() {
        idQuestionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(questionsTable.getItems().indexOf(param.getValue()) + 1));
    }

    private void initThemeColumn() {
        themeColumn.setCellValueFactory(param -> {
            Question ques = param.getValue();
            Theme theme = ques.getTheme();
            if (theme != null) {
                return theme.themeProperty();
            }
            return null;
        });
    }

    private void initTypeColumn() {
        typeColumn.setCellValueFactory(param -> {
            Question ques = param.getValue();
            int type = ques.getQuestionType();
            if (type == 1)
                return new SimpleStringProperty("С одним вариантом ответов");
            else if (type == 2)
                return new SimpleStringProperty("С несколькими вариантами ответов");
            else if (type == 3)
                return new SimpleStringProperty("Текстовый вариант ответа");
            return null;
        });
    }

    private void initCheckQuestionColumn() {
        checkQuestionColumn.setCellValueFactory(param -> param.getValue().inTestProperty());
        checkQuestionColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkQuestionColumn));
    }

    private void initUpdateTest() {
        nameTestField.textProperty().bindBidirectional(selectedTest.testNameProperty());
        selectedSubject = selectedTest.getSubject();
        subjectsBox.getSelectionModel().select(selectedTest.getSubject());
        initQuestionsTable();
        int hours = selectedTest.getTimer().getHours();
        int minutes = selectedTest.getTimer().getMinutes();
        int seconds = selectedTest.getTimer().getSeconds();
        if (hours < 10)
            hoursBox.getSelectionModel().select("0" + hours);
        else
            hoursBox.getSelectionModel().select(hours + "");
        if (minutes < 10)
            minutesBox.getSelectionModel().select("0" + minutes);
        else
            minutesBox.getSelectionModel().select(minutes + "");
        if (seconds < 10)
            secondsBox.getSelectionModel().select("0" + seconds);
        else
            secondsBox.getSelectionModel().select(seconds + "");
    }

    private void initQuestionsTable() {
        for (int i = 0; i < questionsTable.getItems().size(); i++) {
            questionsTable.getItems().get(i).setInTest(false);
        }
        for (int i = 0; i < questionsTable.getItems().size(); i++) {
            for (Question question : selectedTest.getQuestions()) {
                Question inTable = questionsTable.getItems().get(i);
                if (inTable.equals(question))
                    inTable.setInTest(true);
            }
        }
        questionsTable.setItems(questions);
    }

    private void initSearchQuestions() {
        searchResultsTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));
    }

    private class SearchTask extends AsyncTask<Void, Void, ObservableList<Question>> {

        private String newValue;

        private SearchTask(String value) {
            this.newValue = value;
        }


        @Override
        public void onPreExecute() {

        }

        @Override
        public ObservableList<Question> doInBackground(Void... voids) {
            if (newValue != null) {
                Question question = new Question();
                question.setQuestionText(newValue);
                Theme theme = new Theme();
                theme.setThemeText(newValue);
                question.setTheme(theme);
                return new RestConnection().getRestQuestion().search(question);
            } else {
                return new RestConnection().getRestQuestion().getAll();
            }
        }

        @Override
        public void onPostExecute(ObservableList<Question> questions) {
            questionsTable.setItems(questions);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
