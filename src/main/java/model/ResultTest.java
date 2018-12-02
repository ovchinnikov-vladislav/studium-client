package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.Set;

public class ResultTest {
    private Integer idResult;

    @JsonManagedReference
    private User user;

    @JsonManagedReference
    private Test test;
    private Date dateBegin;
    private Date dateEnd;
    private Byte mark;

    @JsonBackReference
    private Set<ResultQuestion> resultQuestions;

    public ResultTest() {

    }

    public Integer getIdResult() {
        return idResult;
    }

    public void setIdResult(Integer idResult) {
        this.idResult = idResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Byte getMark() {
        return mark;
    }

    public void setMark(Byte mark) {
        this.mark = mark;
    }

    public Set<ResultQuestion> getResultQuestions() {
        return resultQuestions;
    }

    public void setResultQuestions(Set<ResultQuestion> resultQuestions) {
        this.resultQuestions = resultQuestions;
    }
}
