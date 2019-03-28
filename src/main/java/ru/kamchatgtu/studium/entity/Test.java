package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.beans.property.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Test {

    private IntegerProperty idTest;
    private StringProperty testName;
    private ObjectProperty<Date> dateEdit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ObjectProperty<Date> timer;
    private ObjectProperty<User> user;
    private ObjectProperty<Subject> subject;
    private Set<Question> questions;

    public Test() {
        idTest = new SimpleIntegerProperty();
        testName = new SimpleStringProperty();
        dateEdit = new SimpleObjectProperty<>();
        timer = new SimpleObjectProperty<>();
        user = new SimpleObjectProperty<>();
        subject = new SimpleObjectProperty<>();
        questions = new HashSet<>();
    }

    public int getIdTest() {
        return idTest.get();
    }

    public IntegerProperty idTestProperty() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest.set(idTest);
    }

    public String getTestName() {
        return testName.get();
    }

    public StringProperty testNameProperty() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName.set(testName);
    }

    public Date getDateEdit() {
        return dateEdit.get();
    }

    public ObjectProperty<Date> dateEditProperty() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit.set(dateEdit);
    }

    public Date getTimer() {
        return timer.get();
    }

    public ObjectProperty<Date> timerProperty() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer.set(timer);
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

    public Subject getSubject() {
        return subject.get();
    }

    public ObjectProperty<Subject> subjectProperty() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject.set(subject);
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(idTest.get(), test.idTest.get()) &&
                Objects.equals(testName.get(), test.testName.get()) &&
                Objects.equals(dateEdit.get(), test.dateEdit.get()) &&
                Objects.equals(timer.get(), test.timer.get()) &&
                Objects.equals(user.get(), test.user.get()) &&
                Objects.equals(subject.get(), test.subject.get()) &&
                Objects.equals(questions, test.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTest.get(), testName.get(), dateEdit.get(), timer.get(), user.get(), subject.get());
    }
}
