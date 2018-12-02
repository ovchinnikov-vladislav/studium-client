package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.Set;

public class Test {
    private Integer idTest;
    private String nameTest;
    private Date dateEdit;
    private Date timer;

    @JsonManagedReference
    private User user;

    @JsonManagedReference
    private Subject subject;

    @JsonBackReference
    private Set<ResultTest> resultTests;

    public Test() {

    }

    public Integer getIdTest() {
        return idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public String getNameTest() {
        return nameTest;
    }

    public void setNameTest(String nameTest) {
        this.nameTest = nameTest;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<ResultTest> getResultTests() {
        return resultTests;
    }

    public void setResultTests(Set<ResultTest> resultTests) {
        this.resultTests = resultTests;
    }
}
