package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable, Cloneable {

    private IntegerProperty idQuestion;
    private StringProperty textQuestion;
    private ObjectProperty<Theme> theme;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private ObjectProperty<Date> dateReg;
    private IntegerProperty typeQuestion;
    private StringProperty dirImage;
    private StringProperty dirAudio;
    private StringProperty dirVideo;
    private ObjectProperty<User> user;
    @JsonIgnore
    private ObservableList<Answer> answers;

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

    public ObservableList<Answer> getAnswers() {
        RestConnection rest = new RestConnection();
        answers = rest.getRestAnswer().getAnswersByQuestion(getIdQuestion());
        return answers;
    }

    @Override
    public String toString() {
        return getTextQuestion();
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
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Question)) return false;

        if (obj == this) return true;

        Question question = (Question) obj;
        return question.getTextQuestion().equals(this.getTextQuestion()) &&
                question.getTypeQuestion() == this.getTypeQuestion() &&
                question.getDateReg().equals(this.getDateReg()) &&
                question.getIdQuestion() == this.getIdQuestion() &&
                question.getDirAudio().equals(this.getDirAudio()) &&
                question.getDirImage().equals(this.getDirImage()) &&
                question.getDirVideo().equals(this.getDirVideo()) &&
                question.getTheme().equals(this.getTheme()) &&
                question.getUser().equals(this.getUser());
    }
}
