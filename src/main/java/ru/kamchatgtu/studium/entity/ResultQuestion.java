package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.kamchatgtu.studium.entity.user.User;

public class ResultQuestion {

    private IntegerProperty idResult;
    private ObjectProperty<Answer> answer;
    private ObjectProperty<User> user;
    private ObjectProperty<Question> question;
    private ObjectProperty<ResultTest> resultTest;

    public ResultQuestion() {
        idResult = new SimpleIntegerProperty();
        answer = new SimpleObjectProperty<>();
        user = new SimpleObjectProperty<>();
        question = new SimpleObjectProperty<>();
        resultTest = new SimpleObjectProperty<>();
    }

    public int getIdResult() {
        return idResult.get();
    }

    public IntegerProperty idResultProperty() {
        return idResult;
    }

    public void setIdResult(int idResult) {
        this.idResult.set(idResult);
    }

    public Answer getAnswer() {
        return answer.get();
    }

    public ObjectProperty<Answer> answerProperty() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer.set(answer);
    }

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public Question getQuestion() {
        return question.get();
    }

    public ObjectProperty<Question> questionProperty() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question.set(question);
    }

    public ResultTest getResultTest() {
        return resultTest.get();
    }

    public ObjectProperty<ResultTest> resultTestProperty() {
        return resultTest;
    }

    public void setResultTest(ResultTest resultTest) {
        this.resultTest.set(resultTest);
    }
}
