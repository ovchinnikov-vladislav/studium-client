package ru.kamchatgtu.studium.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ru.kamchatgtu.studium.controller.work.WorkWindowController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.engine.thread.AnswerQuesTask;
import ru.kamchatgtu.studium.engine.thread.CheckConnectTask;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.message.Message;
import ru.kamchatgtu.studium.view.result.ResultTestWindow;
import ru.kamchatgtu.studium.view.test.TestWindow;
import ru.kamchatgtu.studium.view.work.WorkWindow;

import java.io.IOException;
import java.util.*;

public class TestWindowController {

    private static Test selectedTest;
    private RestConnection rest;
    private Timeline timeLine;
    private ArrayList<Question> questionList;
    private ArrayList<Circle> circles;
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

    @FXML
    private FlowPane questionCheckPanel;

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
            titleLabel.setText("Тест: \u00ab" + selectedTest.getTestName() + "\u00bb");
            prevButton.setDisable(true);
            initTest();
        }
    }

    private void initTest() {
        numberQuestion = 0;
        questionList = new ArrayList<>();
        questionList.addAll(selectedTest.getQuestions());
        for (Question question : questionList)
            question.initAnswers();
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
        initQuestionCheckPanel();
        testStart();
        //CheckConnectTask checkConnectTask = new CheckConnectTask(titleLabel);
        //checkConnectTask.execute();
    }

    private void initQuestionCheckPanel() {
        circles = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            Circle circle = new Circle();
            circle.setRadius(8);
            circles.add(circle);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Set<ResultQuestion> result = resultQuestionsAll.get(numberQuestion);
                if (result.size() > 0)
                    circles.get(numberQuestion).getStyleClass().setAll("circleTest_ans");
                else
                    circles.get(numberQuestion).getStyleClass().setAll("circleTest");
                numberQuestion = circles.indexOf(circle);
                circle.getStyleClass().setAll("circleTest_last");
                initQuestion();
                if (numberQuestion == questionList.size() - 1)
                    nextButton.setDisable(true);
                else
                    nextButton.setDisable(false);
                if (numberQuestion > 0)
                    prevButton.setDisable(false);
                else
                    prevButton.setDisable(true);
            });
            circle.getStyleClass().add("circleTest");
            FlowPane.setMargin(circle, new Insets(1));
            questionCheckPanel.getChildren().add(circle);
        }
        if (circles.size() > 0)
            circles.get(0).getStyleClass().setAll("circleTest_last");
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
        try {
            ResultTestWindowController.setResultTest(rest.getRestResultTest().get(resultTest.getIdResult()));
            Stage stage = ResultTestWindow.getStage();
            stage.initOwner(titleLabel.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            Platform.runLater(stage::showAndWait);
            ((Stage) titleLabel.getScene().getWindow()).close();
            Message message = new Message(Alert.AlertType.INFORMATION);
            message.setTitle("Завершение теста");
            message.setHeaderText("Тест завершен.");
            message.setContentText("Ваше время вышло.");
            message.initOwner(stage);
            message.initModality(Modality.APPLICATION_MODAL);
            Platform.runLater(message::showAndWait);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void nextQuestionAction(ActionEvent event) {
        resultQuestionsAll.set(numberQuestion, resultQuestions);
        if (numberQuestion < questionList.size() - 1) {
            numberQuestion++;
            if (resultQuestions.size() > 0)
                circles.get(numberQuestion-1).getStyleClass().setAll("circleTest_ans");
            else
                circles.get(numberQuestion-1).getStyleClass().setAll("circleTest");
            circles.get(numberQuestion).getStyleClass().setAll("circleTest_last");
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
            if (resultQuestions.size() > 0)
                circles.get(numberQuestion+1).getStyleClass().setAll("circleTest_ans");
            else
                circles.get(numberQuestion+1).getStyleClass().setAll("circleTest");
            circles.get(numberQuestion).getStyleClass().setAll("circleTest_last");
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
      //  question.initAnswers();
        ObservableList<Answer> answers = questionList.get(numberQuestion).getAnswers();
        Collections.shuffle(answers);
        for (Answer answer : answers)
            answer.setMark(false);
        if (resultQuestionsAll != null && resultQuestionsAll.size() > numberQuestion) {
            for (ResultQuestion result : resultQuestionsAll.get(numberQuestion)) {
                for (Answer answer : answers) {
                    if (answer.getAnswerText().equals(result.getAnswer().getAnswerText()))
                        answer.setMark(true);
                }
            }
        }
        int type = question.getQuestionType();
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
        numberLabel.getStyleClass().add("labelBorder");
        numberLabel.setText((row + 1) + ". ");
        GridPane.setValignment(numberLabel, VPos.TOP);
        RadioButton radioButton = new RadioButton();
        radioButton.selectedProperty().bindBidirectional(answer.markProperty());
        int access = SecurityAES.USER_LOGIN.getRole().getAccess();
        if ((access == 1 || access == 2) && answer.isCorrect())
            radioButton.getStyleClass().add("radioButtonTestDot");
        else
            radioButton.getStyleClass().add("radioButtonTest");
        GridPane.setValignment(radioButton, VPos.TOP);
        GridPane.setHalignment(radioButton, HPos.RIGHT);
        radioButton.setToggleGroup(groupAnswers);
        Label labelTextAnswer = new Label();
        labelTextAnswer.setText(answer.getAnswerText());
        labelTextAnswer.getStyleClass().add("labelBorder");
        labelTextAnswer.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(labelTextAnswer, Priority.ALWAYS);
        labelTextAnswer.setWrapText(true);
        GridPane.setValignment(labelTextAnswer, VPos.TOP);
        answersPane.add(numberLabel, 0, row);
        answersPane.add(labelTextAnswer, 1, row);
        answersPane.add(radioButton, 2, row);
        if (answer.isMark()) {
            radioButton.setSelected(true);
            numberLabel.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: #ffffff;");
            labelTextAnswer.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: rgba(255,255,255,1);");
        }
        initRadioButtonEvent(radioButton, numberLabel, labelTextAnswer, answer);
    }

    private void initRadioButtonEvent(RadioButton radioButton, Label numberLabel, Label labelTextAnswer, Answer answer) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            initSelect(answer, radioButton.isSelected(), false);
            if (radioButton.isSelected()) {
                numberLabel.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: #ffffff;");
                labelTextAnswer.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: rgba(255,255,255,1);");
            } else {
                numberLabel.setStyle("-fx-background-color: white");
                labelTextAnswer.setStyle("-fx-background-color: white");
            }
        });
    }

    private void initMultiAnswers(ObservableList<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            initMultiAnswer(i, answers.get(i));
        }
    }

    private void initMultiAnswer(int row, Answer answer) {
        Label numberLabel = new Label();
        numberLabel.getStyleClass().add("labelBorder");
        numberLabel.setText((row + 1) + ". ");
        GridPane.setValignment(numberLabel, VPos.TOP);
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().bindBidirectional(answer.markProperty());
        int access = SecurityAES.USER_LOGIN.getRole().getAccess();
        if ((access == 1 || access == 2) && answer.isCorrect())
            checkBox.getStyleClass().add("checkBoxTestMark");
        else
            checkBox.getStyleClass().add("checkBoxTest");
        GridPane.setValignment(checkBox, VPos.TOP);
        GridPane.setHalignment(checkBox, HPos.RIGHT);
        Label labelTextAnswer = new Label();
        labelTextAnswer.setText(answer.getAnswerText());
        labelTextAnswer.getStyleClass().add("labelBorder");
        labelTextAnswer.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(labelTextAnswer, Priority.ALWAYS);
        labelTextAnswer.setWrapText(true);
        GridPane.setValignment(labelTextAnswer, VPos.TOP);
        answersPane.add(numberLabel, 0, row);
        answersPane.add(labelTextAnswer, 1, row);
        answersPane.add(checkBox, 2, row);
        if (answer.isMark()) {
            checkBox.setSelected(true);
            numberLabel.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: #ffffff;");
            labelTextAnswer.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: rgba(255,255,255,1);");
        }
        initCheckBoxEvent(checkBox, numberLabel, labelTextAnswer, answer);
    }

    private void initCheckBoxEvent(CheckBox checkBox, Label numberLabel, Label labelTextAnswer, Answer answer) {
        checkBox.selectedProperty().addListener(observable -> {
            initSelect(answer, checkBox.isSelected(), true);
            if (checkBox.isSelected()) {
                numberLabel.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: #fff;");
                labelTextAnswer.setStyle("-fx-background-color: rgba(114,140,183,0.63); -fx-text-fill: #fff;");
            } else {
                numberLabel.setStyle("-fx-background-color: white");
                labelTextAnswer.setStyle("-fx-background-color: white");
            }
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
        textField.textProperty().bindBidirectional(answers.get(0).answerTextProperty());
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
       /*ButtonType closeTest = new ButtonType("Завершить");
        ButtonType cancel = new ButtonType("Отменить");*/

        Message message = new Message(Alert.AlertType.CONFIRMATION);
       /*message.getButtonTypes().clear();
        message.getButtonTypes().addAll(closeTest, cancel);*/
        message.setTitle("Завершение теста");
        message.setHeaderText("Завершение теста");
        message.setContentText("Вы действительно хотите завершить тестирование?");

        Optional<ButtonType> option = message.showAndWait();

        if (option.get() == ButtonType.OK) {
            fixResult();
            timeLine.stop();
            openResult(rest.getRestResultTest().get(resultTest.getIdResult()));
            stage.close();
        } else {
            message.close();
        }
    }

    private void openResult(ResultTest row) {
        try {
            ResultTestWindowController.setResultTest(row);
            Stage stage = ResultTestWindow.getStage();
            stage.initOwner(titleLabel.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
