package ru.kamchatgtu.studium.controller.work;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CreateQuesPanelController {

    private RestConnection restConnection;
    private Set<Answer> querySet;


    //-------------Вопросы------------------
    // Поля раздела "Вопросы"
    private ObservableList<Theme> themes;
    private ObservableList<Question> questions;
    private ObservableList<Answer> answers;
    private Question selectQuestion;
    // Компоненты раздела "Вопросы"
    @FXML
    private ComboBox<Theme> themeBox;
    @FXML
    private ComboBox<Question> questionBox;
    @FXML
    private TextField questionField;
    @FXML
    private Button addQuestionButton;
    @FXML
    private Button deleteQuestionButton;
    @FXML
    private Button saveQuestionButton;
    @FXML
    private GridPane answersPane;
    @FXML
    private Button addAnswerButton;

    // Методы раздела "Вопросы"
    @FXML
    public void initialize() {
        querySet = new HashSet<>();
        restConnection = new RestConnection();
        themes = restConnection.getRestTheme().getAll();
        themeBox.getItems().addAll(themes);
        themeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (questionBox.isVisible()) {
                Integer id = themeBox.getValue().getIdTheme();
                questionBox.getItems().addAll(restConnection.getRestQuestion().getQuestionsByTheme(newValue.getIdTheme()));
                Question question = questionBox.getItems().get(0);
                System.out.println(question.getTheme().getIdTheme());
            }
        });
        questionBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectQuestion = newValue;
            questionBox.setVisible(false);
            questionField.setVisible(true);
            questionField.textProperty().bindBidirectional(newValue.textQuestionProperty());
            answers = restConnection.getRestAnswer().getAnswersByQuestion(newValue.getIdQuestion());
            initAnswers();
            addQuestionButton.setDisable(true);
            deleteQuestionButton.setDisable(false);
            saveQuestionButton.setDisable(false);
            addAnswerButton.setDisable(false);
        });
    }

    @FXML
    public void addNewQuestionAction(ActionEvent event) {
        selectQuestion = new Question();
        addQuestionButton.setDisable(true);
        deleteQuestionButton.setDisable(false);
        saveQuestionButton.setDisable(false);
        addAnswerButton.setDisable(false);
        questionBox.setVisible(false);
        questionField.setVisible(true);
        themeBox.valueProperty().bindBidirectional(selectQuestion.themeProperty());
        questionField.textProperty().bindBidirectional(selectQuestion.textQuestionProperty());
        questions = FXCollections.observableArrayList();
        questions.add(selectQuestion);
        answers = FXCollections.observableArrayList();
        selectQuestion.setTextQuestion("");
        selectQuestion.setTypeQuestion(1);
        selectQuestion.setUser(Security.userLogin);
        Answer answer = new Answer();
        answer.setTextAnswer("");
        answer.setUser(Security.userLogin);
        answers.add(answer);
        initAnswers();
    }

    @FXML
    public void saveQuestionButton(ActionEvent event) {
        Question question;
        if (selectQuestion.getDateReg() == null) {
            selectQuestion.setDateReg(new Date());
            question = restConnection.getRestQuestion().add(selectQuestion);
        } else {
            question = restConnection.getRestQuestion().update(selectQuestion);
        }
        if (question != null) {
            for (Answer answer : answers) {
                answer.setQuestion(question);
                if (answer.getDateEdit() == null) {
                    answer.setDateEdit(new Date());
                    restConnection.getRestAnswer().add(answer);
                } else {
                    restConnection.getRestAnswer().update(answer);
                }
            }
            for (Answer answer : querySet)
                restConnection.getRestAnswer().remove(answer);
        }
        questionField.setVisible(false);
        questionBox.setVisible(true);
        questionBox.getSelectionModel().clearSelection();
        questionBox.setValue(null);
        addQuestionButton.setDisable(false);
        deleteQuestionButton.setDisable(true);
        saveQuestionButton.setDisable(true);
        addAnswerButton.setDisable(true);
        answersPane.getChildren().removeAll(answersPane.getChildren());
    }

    @FXML
    public void addNewAnswerAction(ActionEvent event) {
        Answer answer = new Answer();
        answer.setUser(Security.userLogin);
        answers.add(answer);
        initAnswers();
    }

    private void initAnswers() {
        int row = 0;
        answersPane.getChildren().removeAll(answersPane.getChildren());
        for (Answer answer : answers) {
            Label label = new Label();
            label.setText(row + 1 + ".");
            TextField textField = new TextField();
            textField.textProperty().bindBidirectional(answer.textAnswerProperty());
            Button button = new Button("-");
            button.getStyleClass().add("buttonRound");
            button.setStyle("-fx-padding: 5px 9px 5px 9px");
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().bindBidirectional(answer.rightProperty());
            button.setOnAction((eventBut) -> {
                if (answers.size() > 1) {
                    answersPane.getChildren().remove(textField);
                    answersPane.getChildren().remove(button);
                    answersPane.getChildren().remove(label);
                    answersPane.getChildren().remove(checkBox);
                    querySet.add(answer);
                    answers.remove(answer);
                    initAnswers();
                }
            });
            answersPane.add(label, 0, row);
            answersPane.add(checkBox, 1, row);
            answersPane.add(textField, 2, row);
            answersPane.add(button, 3, row);
            row++;
        }
    }
    //---------------------------------------------
}
