package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class ResultQuestion {

    private IntegerProperty idResultQuestion;
    private ObjectProperty<Answer> answer;
    private ObjectProperty<User> user;
    private ObjectProperty<Question> question;
    private ObjectProperty<ResultTest> resultTest;

    public ResultQuestion() {
        idResultQuestion = new SimpleIntegerProperty();
        answer = new SimpleObjectProperty<>();
        user = new SimpleObjectProperty<>();
        question = new SimpleObjectProperty<>();
        resultTest = new SimpleObjectProperty<>();
    }

    public Answer getAnswer() {
        return answer.get();
    }

    public int getIdResultQuestion() {
        return idResultQuestion.get();
    }

    public IntegerProperty idResultQuestionProperty() {
        return idResultQuestion;
    }

    public void setIdResultQuestion(int idResultQuestion) {
        this.idResultQuestion.set(idResultQuestion);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultQuestion that = (ResultQuestion) o;
        return Objects.equals(answer.get(), that.answer.get()) &&
                Objects.equals(user.get(), that.user.get()) &&
                Objects.equals(question.get(), that.question.get()) &&
                Objects.equals(resultTest.get(), that.resultTest.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer.get().getAnswerText(), user.get().getLogin(), question.get().getQuestionText(), resultTest.get().getIdResult());
    }
}
