package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.beans.property.*;
import ru.kamchatgtu.studium.entity.user.User;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class Test {

    private IntegerProperty idTest;
    private StringProperty nameTest;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private ObjectProperty<Date> dateEdit;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private ObjectProperty<Date> timer;
    private ObjectProperty<User> user;
    private ObjectProperty<Subject> subject;

    public Test() {
        idTest = new SimpleIntegerProperty();
        nameTest = new SimpleStringProperty();
        dateEdit = new SimpleObjectProperty<>();
        timer = new SimpleObjectProperty<>();
        user = new SimpleObjectProperty<>();
        subject = new SimpleObjectProperty<>();
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

    public String getNameTest() {
        return nameTest.get();
    }

    public StringProperty nameTestProperty() {
        return nameTest;
    }

    public void setNameTest(String nameTest) {
        this.nameTest.set(nameTest);
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
}
