package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Question implements Serializable, Cloneable {

    private IntegerProperty idQuestion;
    private StringProperty textQuestion;
    private ObjectProperty<Theme> theme;
    private ObjectProperty<Date> dateReg;
    private IntegerProperty typeQuestion;
    private StringProperty dirImage;
    private StringProperty dirAudio;
    private StringProperty dirVideo;
    private ObjectProperty<User> user;
    @JsonIgnore
    private ObservableList<Answer> answers;
    @JsonIgnore
    private BooleanProperty inTest;

    public Question() {
        idQuestion = new SimpleIntegerProperty();
        textQuestion = new SimpleStringProperty();
        theme = new SimpleObjectProperty<>();
        dateReg = new SimpleObjectProperty<>();
        typeQuestion = new SimpleIntegerProperty();
        dirImage = new SimpleStringProperty();
        dirAudio = new SimpleStringProperty();
        dirVideo = new SimpleStringProperty();
        user = new SimpleObjectProperty<>();
        inTest = new SimpleBooleanProperty();
    }

    public int getIdQuestion() {
        return idQuestion.get();
    }

    public IntegerProperty idQuestionProperty() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion.set(idQuestion);
    }

    public String getTextQuestion() {
        return textQuestion.get();
    }

    public StringProperty textQuestionProperty() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion.set(textQuestion);
    }

    public Theme getTheme() {
        return theme.get();
    }

    public void setTheme(Theme theme) {
        this.theme.set(theme);
    }

    public ObjectProperty<Theme> themeProperty() {
        return theme;
    }

    public Date getDateReg() {
        return dateReg.get();
    }

    public ObjectProperty<Date> dateRegProperty() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg.set(dateReg);
    }

    public int getTypeQuestion() {
        return typeQuestion.get();
    }

    public IntegerProperty typeQuestionProperty() {
        return typeQuestion;
    }

    public void setTypeQuestion(int typeQuestion) {
        this.typeQuestion.set(typeQuestion);
    }

    public String getDirImage() {
        return dirImage.get();
    }

    public StringProperty dirImageProperty() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage.set(dirImage);
    }

    public String getDirAudio() {
        return dirAudio.get();
    }

    public StringProperty dirAudioProperty() {
        return dirAudio;
    }

    public void setDirAudio(String dirAudio) {
        this.dirAudio.set(dirAudio);
    }

    public String getDirVideo() {
        return dirVideo.get();
    }

    public StringProperty dirVideoProperty() {
        return dirVideo;
    }

    public void setDirVideo(String dirVideo) {
        this.dirVideo.set(dirVideo);
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

    public void initAnswers() {
        RestConnection rest = new RestConnection();
        answers = rest.getRestAnswer().getAnswersByQuestion(getIdQuestion());
    }

    public ObservableList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ObservableList<Answer> answers) {
        this.answers = answers;
    }

    public boolean isInTest() {
        return inTest.get();
    }

    public void setInTest(boolean inTest) {
        this.inTest.set(inTest);
    }

    public BooleanProperty inTestProperty() {
        return inTest;
    }

    @Override
    public String toString() {
        return textQuestion.get();
    }

    @Override
    public Object clone() {
        Question question = new Question();
        question.setTypeQuestion(getTypeQuestion());
        question.setTheme(getTheme());
        question.setDateReg(getDateReg());
        question.setDirAudio(getDirAudio());
        question.setDirImage(getDirImage());
        question.setDirVideo(getDirVideo());
        question.setIdQuestion(getIdQuestion());
        question.setTextQuestion(getTextQuestion());
        question.setUser(getUser());
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(idQuestion.get(), question.idQuestion.get()) &&
                Objects.equals(textQuestion.get(), question.textQuestion.get()) &&
                Objects.equals(theme.get(), question.theme.get()) &&
                Objects.equals(dateReg.get(), question.dateReg.get()) &&
                Objects.equals(typeQuestion.get(), question.typeQuestion.get()) &&
                Objects.equals(dirImage.get(), question.dirImage.get()) &&
                Objects.equals(dirAudio.get(), question.dirAudio.get()) &&
                Objects.equals(dirVideo.get(), question.dirVideo.get()) &&
                Objects.equals(user.get(), question.user.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion.get(), textQuestion.get(), theme.get(), dateReg.get(), typeQuestion.get(), dirImage.get(), dirAudio.get(), dirVideo.get(), user.get());
    }
}
