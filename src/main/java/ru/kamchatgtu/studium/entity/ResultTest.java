package ru.kamchatgtu.studium.entity;

import javafx.beans.property.*;
import ru.kamchatgtu.studium.entity.user.User;

import java.util.Date;
import java.util.Objects;

public class ResultTest {

    private IntegerProperty idResult;
    private ObjectProperty<User> user;
    private ObjectProperty<Test> test;
    private ObjectProperty<Date> dateBegin;
    private ObjectProperty<Date> dateEnd;
    private FloatProperty mark;

    public ResultTest() {
        idResult = new SimpleIntegerProperty();
        user = new SimpleObjectProperty<>();
        test = new SimpleObjectProperty<>();
        dateBegin = new SimpleObjectProperty<>();
        dateEnd = new SimpleObjectProperty<>();
        mark = new SimpleFloatProperty();
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

    public float getMark() {
        return mark.get();
    }

    public void setMark(float mark) {
        this.mark.set(mark);
    }

    public FloatProperty markProperty() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTest that = (ResultTest) o;
        return Objects.equals(idResult.get(), that.idResult.get()) &&
                Objects.equals(user.get(), that.user.get()) &&
                Objects.equals(test.get(), that.test.get()) &&
                Objects.equals(dateBegin.get(), that.dateBegin.get()) &&
                Objects.equals(dateEnd.get(), that.dateEnd.get()) &&
                Objects.equals(mark.get(), that.mark.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResult.get(), user.get(), test.get(), dateBegin.get(), dateEnd.get(), mark.get());
    }
}
