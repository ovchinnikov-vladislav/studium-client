package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.beans.property.*;
import ru.kamchatgtu.studium.entity.user.User;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {

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

    @Override
    public String toString() {
        return getTextQuestion();
    }
}
