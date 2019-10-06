package ru.kamchatgtu.studium.engine.thread;

import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.entity.ResultQuestion;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Set;


public class AnswerQuesTask implements Runnable {

    private RestConnection rest;
    private ResultQuestion resultQuestion;
    private Set<ResultQuestion> resultQuestions;

    public AnswerQuesTask(ResultQuestion resultQuestion, Set<ResultQuestion> resultQuestions) {
        this.rest = new RestConnection();
        this.resultQuestions = resultQuestions;
        this.resultQuestion = resultQuestion;
    }

    @Override
    public void run() {
        ObservableList<ResultQuestion> oldResultQuestions = rest.getRestResultQuestion().getByQuestionAndResultTest(resultQuestion.getQuestion().getIdQuestion(), resultQuestion.getResultTest().getIdResult());
        if (oldResultQuestions.size() > 0)
            oldResultQuestions.forEach(action -> rest.getRestResultQuestion().remove(action));
        if (resultQuestions.size() > 0)
            resultQuestions.forEach(action -> rest.getRestResultQuestion().add(action));
    }
}
