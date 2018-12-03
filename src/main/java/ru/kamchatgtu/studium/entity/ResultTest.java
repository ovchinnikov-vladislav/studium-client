package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.kamchatgtu.studium.entity.user.User;

import java.util.Date;

public class ResultTest {

    private IntegerProperty idResult;
    private ObjectProperty<User> user;
    private ObjectProperty<Test> test;
    private ObjectProperty<Date> dateBegin;
    private ObjectProperty<Date> dateEnd;
    private IntegerProperty mark;

    public ResultTest() {
        idResult = new SimpleIntegerProperty();
        user = new SimpleObjectProperty<>();
        test = new SimpleObjectProperty<>();
        dateBegin = new SimpleObjectProperty<>();
        dateEnd = new SimpleObjectProperty<>();
        mark = new SimpleIntegerProperty();
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

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public Test getTest() {
        return test.get();
    }

    public ObjectProperty<Test> testProperty() {
        return test;
    }

    public void setTest(Test test) {
        this.test.set(test);
    }

    public Date getDateBegin() {
        return dateBegin.get();
    }

    public ObjectProperty<Date> dateBeginProperty() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin.set(dateBegin);
    }

    public Date getDateEnd() {
        return dateEnd.get();
    }

    public ObjectProperty<Date> dateEndProperty() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    public int getMark() {
        return mark.get();
    }

    public IntegerProperty markProperty() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark.set(mark);
    }
}
