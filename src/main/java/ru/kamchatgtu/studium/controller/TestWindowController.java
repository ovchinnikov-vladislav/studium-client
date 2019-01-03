package ru.kamchatgtu.studium.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.engine.thread.AnswerQuesTask;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.message.Message;

import java.util.*;

public class TestWindowController {

    private static Test selectedTest;
    private RestConnection rest;
    private Timeline timeLine;
    private ArrayList<Question> questionList;
    private ResultTest resultTest;
    private ArrayList<Set<ResultQuestion>> resultQuestionsAll;
    private Set<ResultQuestion> resultQuestions;

    private int numberQuestion;
    @FXML
    private Label titleLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private GridPane answersPane;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    private ToggleGroup groupAnswers;

    @FXML
    private Label hoursLabel;
    @FXML
    private Label minutesLabel;
    @FXML
    private Label timeLabel;
    private boolean isLoad = false;
    private EventHandler<WindowEvent> closeEventHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            event.consume();
            if (timeLine.getStatus() != Animation.Status.STOPPED)
                initCloseMessage((Stage) event.getSource());
        }
    };

    public static Test getSelectedTest() {
        return selectedTest;
    }

    public static void setSelectedTest(Test selectedTest) {
        TestWindowController.selectedTest = selectedTest;
    }

    @FXML
    public void initialize() {
        rest = new RestConnection();
        groupAnswers = new ToggleGroup();
        if (selectedTest != null) {
            titleLabel.setText("Тест: \u00ab" + selectedTest.getNameTest() + "\u00bb");
            prevButton.setDisable(true);
            initTest();
        }
    }

    private void initTest() {
        numberQuestion = 0;
        questionList = new ArrayList<>();
        questionList.addAll(selectedTest.getQuestions());
        if (questionList.size() == 0)
            nextButton.setDisable(true);
        Collections.shuffle(questionList);
        resultTest = new ResultTest();
        resultTest.setDateBegin(new Date());
        resultTest.setDateEnd(new Date(selectedTest.getTimer().getTime() + resultTest.getDateBegin().getTime()));
        resultTest.setUser(SecurityAES.USER_LOGIN);
        resultTest.setTest(selectedTest);
        resultTest = rest.getRestResultTest().add(resultTest);
        resultQuestionsAll = new ArrayList<>();
        questionList.forEach(action -> resultQuestionsAll.add(new HashSet<>()));
        initQuestion();
        testStart();
    }

    private void testStart() {
        Date date = selectedTest.getTimer();
        int[] time = {date.getHours(), date.getMinutes(), date.getSeconds()};
        initTime(time);
        timeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            if (time[2] == 0) {
                                if (time[1] == 0) {
                                    time[1] = 59;
                                    if (time[0] > 0)
                                        time[0]--;
                                } else time[1]--;
                                time[2] = 59;
                            } else
                                time[2]--;
                            initTime(time);
                        }
                )
        );
        timeLine.setCycleCount(time[0] * 60 * 60 + time[1] * 60 + time[2] + 1);
        timeLine.play();
        timeLine.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED && time[0] == 0 && time[1] == 59 && time[2] == 59) {
                finishedTime();
            }
        });
    }

    private void initTime(int[] time) {
        if (time[0] > 0) {
            if (time[0] < 10)
                hoursLabel.setText("0" + time[0]);
            else
                hoursLabel.setText(time[0] + "");
            if (time[1] < 10)
                minutesLabel.setText("0" + time[1]);
            else
                minutesLabel.setText(time[1] + "");
            if (timeLabel.isVisible())
                timeLabel.setVisible(false);
            else
                timeLabel.setVisible(true);
        } else {
            timeLabel.setVisible(true);
            if (time[1] < 10)
                hoursLabel.setText("0" + time[1]);
            else
                hoursLabel.setText(time[1] + "");
            if (time[2] < 10)
                minutesLabel.setText("0" + time[2]);
            else
                minutesLabel.setText(time[2] + "");
        }
    }

    private void finishedTime() {
        fixResult();
        Message message = new Message(Alert.AlertType.INFORMATION);
        message.setTitle("Завершение теста");
        message.setHeaderText("Тест завершен.");
        message.setContentText("Ваше время вышло.");
        message.initOwner(hoursLabel.getScene().getWindow());
        message.initModality(Modality.APPLICATION_MODAL);
        Platform.runLater(message::showAndWait);
        ((Stage) titleLabel.getScene().getWindow()).close();
    }

    @FXML
    public void nextQuestionAction(ActionEvent event) {
        resultQuestionsAll.set(numberQuestion, resultQuestions);
        if (numberQuestion < questionList.size() - 1) {
            numberQuestion++;
            initQuestion();
            if (numberQuestion == questionList.size() - 1)
                nextButton.setDisable(true);
            if (numberQuestion > 0)
                prevButton.setDisable(false);
        }
    }

    @FXML
    public void prevQuestionAction(ActionEvent event) {
        resultQuestionsAll.set(numberQuestion, resultQuestions);
        if (numberQuestion > 0) {
            numberQuestion--;
            initQuestion();
            if (numberQuestion == 0)
                prevButton.setDisable(true);
            if (numberQuestion < questionList.size() - 1)
                nextButton.setDisable(false);
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        initCloseMessage((Stage) titleLabel.getScene().getWindow());
    }

    private void initQuestion() {
        resultQuestions = resultQuestionsAll.get(numberQuestion);
        if (questionList.size() > 0) {
            questionLabel.setText((numberQuestion + 1) + ". " + questionList.get(numberQuestion));
        }
        initAnswers();
    }

    private void initAnswers() {
        isLoad = true;
        clearAnswers();
        Question question = questionList.get(numberQuestion);
        question.initAnswers();
        ObservableList<Answer> answers = questionList.get(numberQuestion).getAnswers();
        Collections.shuffle(answers);
        for (Answer answer : answers)
            answer.setRight(false);
        if (resultQuestionsAll != null && resultQuestionsAll.size() > numberQuestion) {
            for (ResultQuestion result : resultQuestionsAll.get(numberQuestion)) {
                for (Answer answer : answers) {
                    if (answer.getTextAnswer().equals(result.getAnswer().getTextAnswer()))
                        answer.setRight(true);
                }
            }
        }
        int type = question.getTypeQuestion();
        if (type == 1) {
            initOneAnswers(answers);
        } else if (type == 2) {
            initMultiAnswers(answers);
        } else if (type == 3) {
            initTextAnswers(answers);
        }
        isLoad = false;
    }

    private void clearAnswers() {
        answersPane.getChildren().removeAll(answersPane.getChildren());
    }

    private void initOneAnswers(ObservableList<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            initOneAnswer(i, answers.get(i));
        }
    }

    private void initOneAnswer(int row, Answer answer) {
        Label numberLabel = new Label();
        numberLabel.setStyle("-fx-text-fill: #728cb7; -fx-font-size: 14");
        numberLabel.setText((row + 1) + ". ");
        GridPane.setValignment(numberLabel, VPos.TOP);
        RadioButton radioButton = new RadioButton();
        radioButton.selectedProperty().bindBidirectional(answer.rightProperty());
        GridPane.setValignment(radioButton, VPos.TOP);
        GridPane.setHalignment(radioButton, HPos.RIGHT);
        radioButton.setToggleGroup(groupAnswers);
        Label labelTextAnswer = new Label();
        labelTextAnswer.setText(answer.getTextAnswer());
        labelTextAnswer.setStyle("-fx-text-fill: #728cb7; -fx-font-size: 14");
        labelTextAnswer.setWrapText(true);
        GridPane.setValignment(labelTextAnswer, VPos.TOP);
        answersPane.add(numberLabel, 0, row);
        answersPane.add(labelTextAnswer, 1, row);
        answersPane.add(radioButton, 2, row);
        if (answer.getRight()) {
            radioButton.setSelected(true);
        }
        initRadioButtonEvent(radioButton, answer);
    }

    private void initRadioButtonEvent(RadioButton radioButton, Answer answer) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            initSelect(answer, radioButton.isSelected(), false);
        });
    }

    private void initMultiAnswers(ObservableList<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            initMultiAnswer(i, answers.get(i));
        }
    }

    private void initMultiAnswer(int row, Answer answer) {
        Label numberLabel = new Label();
        numberLabel.setStyle("-fx-text-fill: #728cb7; -fx-font-size: 14");
        numberLabel.setText((row + 1) + ". ");
        GridPane.setValignment(numberLabel, VPos.TOP);
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().bindBidirectional(answer.rightProperty());
        GridPane.setValignment(checkBox, VPos.TOP);
        GridPane.setHalignment(checkBox, HPos.RIGHT);
        Label labelTextAnswer = new Label();
        labelTextAnswer.setText(answer.getTextAnswer());
        labelTextAnswer.setStyle("-fx-text-fill: #728cb7; -fx-font-size: 14");
        labelTextAnswer.setWrapText(true);
        GridPane.setValignment(labelTextAnswer, VPos.TOP);
        answersPane.add(numberLabel, 0, row);
        answersPane.add(labelTextAnswer, 1, row);
        answersPane.add(checkBox, 2, row);
        if (answer.getRight()) {
            checkBox.setSelected(true);
        }
        initCheckBoxEvent(checkBox, answer);
    }

    private void initCheckBoxEvent(CheckBox checkBox, Answer answer) {
        checkBox.selectedProperty().addListener(observable -> {
            initSelect(answer, checkBox.isSelected(), true);
        });
    }

    private void initSelect(Answer answer, boolean isSelect, boolean checkBox) {
        if (!isLoad) {
            ResultQuestion resultQuestion = new ResultQuestion();
            resultQuestion.setResultTest(resultTest);
            resultQuestion.setUser(SecurityAES.USER_LOGIN);
            resultQuestion.setAnswer(answer);
            resultQuestion.setQuestion(questionList.get(numberQuestion));
            if (isSelect && checkBox) {
                resultQuestions.add(resultQuestion);
            } else if (checkBox) {
                resultQuestions.remove(resultQuestion);
            } else if (isSelect) {
                resultQuestions.add(resultQuestion);
            } else {
                resultQuestions.remove(resultQuestion);
            }
            AnswerQuesTask answerQuesAsync = new AnswerQuesTask(resultQuestion, resultQuestions);
            Thread thread = new Thread(answerQuesAsync);
            thread.start();
            resultQuestionsAll.set(numberQuestion, resultQuestions);
        }
    }

    private void initTextAnswers(ObservableList<Answer> answers) {
        TextField textField = new TextField();
        textField.setPromptText("Напишите свой ответ");
        textField.textProperty().bindBidirectional(answers.get(0).textAnswerProperty());
        answersPane.add(textField, 2, 0);
    }

    private void fixResult() {
        resultTest.setDateEnd(new Date());
        rest.getRestResultTest().fixResult(resultTest);
    }

    public EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

    private void initCloseMessage(Stage stage) {
        ButtonType closeTest = new ButtonType("Завершить");
        ButtonType cancel = new ButtonType("Отменить");

        Message message = new Message(Alert.AlertType.CONFIRMATION);
        message.getButtonTypes().clear();
        message.getButtonTypes().addAll(closeTest, cancel);
        message.setTitle("Завершение теста");
        message.setHeaderText("Завершение теста");
        message.setContentText("Вы действительно хотите завершить тестирование?");

        message.setOnCloseRequest(event -> {
            message.close();
        });

        Optional<ButtonType> option = message.showAndWait();

        if (option.get() == closeTest) {
            fixResult();
            timeLine.stop();
            stage.close();
        }
    }
}
