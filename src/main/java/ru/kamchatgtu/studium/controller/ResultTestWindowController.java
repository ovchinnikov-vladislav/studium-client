package ru.kamchatgtu.studium.controller;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.entity.ResultQuestion;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ResultTestWindowController {

    private static ResultTest resultTest;
    private int mistake;
    private HashMap<Integer, ObservableList<ResultQuestion>> mapResultQuestions;

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label testLabel, nameStudentLabel, groupStudentLabel, countMistakeLabel, markLabel;
    @FXML
    private GridPane questionsPane;

    @FXML
    public void initialize() {
        if (resultTest != null) {
            int access = resultTest.getUser().getRole().getAccess();
            if (access == 2 || access == 3) {
                testLabel.setText("Тест: " + resultTest.getTest().getTestName());
                nameStudentLabel.setText("Студент: " + resultTest.getUser().getFio());
                groupStudentLabel.setText("Группа: " + resultTest.getUser().getGroup().getGroupName());
                Set<Question> questions = resultTest.getTest().getQuestions();
                DownloadQuestionAsync downloadQuestionAsync = new DownloadQuestionAsync(questions);
                downloadQuestionAsync.execute();
            }
        }
    }

    private void initAllQuestions(Set<Question> questions) {
        int i = 0;
        mistake = questions.size();
        for (Question question : questions) {
            initQuestion(i++, question);
        }
    }

    private void initQuestion(int row, Question question) {
        ObservableList<ResultQuestion> resultQuestions = mapResultQuestions.get(question.getIdQuestion());
        ObservableList<Answer> answers = question.getAnswers();
        Label numberLabel = new Label();
        numberLabel.getStyleClass().add("labelStyle");
        numberLabel.setText((row+1) + ". ");
        GridPane.setValignment(numberLabel, VPos.TOP);
        Label labelTextQuestion = new Label();
        labelTextQuestion.setText(question.getQuestionText());
        labelTextQuestion.getStyleClass().add("labelBorder");
        labelTextQuestion.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-text-fill: black;");
        boolean isRight = false;
        int countRight = 0;
        for (Answer answer : answers)
            if (answer.isCorrect())
                countRight++;
        if (resultQuestions.size() != countRight) {
            isRight = false;
        } else {
            for (ResultQuestion resultQuestion : resultQuestions) {
                if (resultQuestion.getAnswer().isCorrect())
                    isRight = true;
                else {
                    isRight = false;
                    break;
                }
            }
        }
        if (isRight) {
            labelTextQuestion.setStyle("-fx-background-color: rgba(0, 255, 0, 0.3); -fx-text-fill: black;");
            mistake--;
        }
        labelTextQuestion.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(labelTextQuestion, Priority.ALWAYS);
        labelTextQuestion.setWrapText(true);
        GridPane.setValignment(labelTextQuestion, VPos.TOP);
        questionsPane.add(numberLabel, 0, row);
        questionsPane.add(labelTextQuestion, 1, row);
    }

    public static void setResultTest(ResultTest resultTest) {
        ResultTestWindowController.resultTest = resultTest;
    }

    private class DownloadQuestionAsync extends AsyncTask<Void, Void, Boolean> {

        private Set<Question> questions;

        public DownloadQuestionAsync(Set<Question> questions) {
            this.questions = questions;
            mapResultQuestions = new HashMap<>();
        }

        @Override
        public void onPreExecute() {
            progressIndicator.setVisible(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            fixResult();
            resultTest = new RestConnection().getRestResultTest().get(resultTest.getIdResult());
            for (Question question : questions) {
                question.initAnswers();
                ObservableList<ResultQuestion> resultQuestions = new RestConnection().getRestResultQuestion().getByQuestionAndResultTest(question.getIdQuestion(), resultTest.getIdResult());
                mapResultQuestions.put(question.getIdQuestion(), resultQuestions);
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                progressIndicator.setVisible(false);
                initAllQuestions(questions);
                countMistakeLabel.setText("Количество ошибок: " + mistake);
                markLabel.setText("Количество баллов: " + resultTest.getMark());
            }
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private void fixResult() {
        Date newDate = new Date();
        if (newDate.getTime() < resultTest.getDateEnd().getTime())
            resultTest.setDateEnd(newDate);
        new RestConnection().getRestResultTest().fixResult(resultTest);
    }
}
