package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ResultQuestion {
    private Integer idResult;

    @JsonManagedReference
    private Answer answer;

    @JsonManagedReference
    private User user;

    @JsonManagedReference
    private Question question;

    @JsonManagedReference
    private ResultTest resultTest;

    public ResultQuestion() {}

    public Integer getIdResult() {
        return idResult;
    }

    public void setIdResult(Integer idResult) {
        this.idResult = idResult;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ResultTest getResultTest() {
        return resultTest;
    }

    public void setResultTest(ResultTest resultTest) {
        this.resultTest = resultTest;
    }
}
