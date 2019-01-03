package ru.kamchatgtu.studium.entity;

import javafx.beans.property.*;
import ru.kamchatgtu.studium.entity.user.User;

import java.util.Date;
import java.util.Objects;

public class Answer {

    private IntegerProperty idAnswer;
    private StringProperty textAnswer;
    private ObjectProperty<Date> dateEdit;
    private StringProperty dirImage;
    private StringProperty dirAudio;
    private StringProperty dirVideo;
    private BooleanProperty right;
    private ObjectProperty<User> user;
    private ObjectProperty<Question> question;

    public Answer() {
        idAnswer = new SimpleIntegerProperty();
        textAnswer = new SimpleStringProperty();
        dateEdit = new SimpleObjectProperty<>();
        dirImage = new SimpleStringProperty();
        dirAudio = new SimpleStringProperty();
        dirVideo = new SimpleStringProperty();
        right = new SimpleBooleanProperty();
        user = new SimpleObjectProperty<>();
        question = new SimpleObjectProperty<>();
    }

    public int getIdAnswer() {
        return idAnswer.get();
    }

    public IntegerProperty idAnswerProperty() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer.set(idAnswer);
    }

    public String getTextAnswer() {
        return textAnswer.get();
    }

    public StringProperty textAnswerProperty() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer.set(textAnswer);
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

    public boolean getRight() {
        return right.get();
    }

    public void setRight(boolean right) {
        this.right.set(right);
    }

    public BooleanProperty rightProperty() {
        return right;
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

    public Question getQuestion() {
        return question.get();
    }

    public ObjectProperty<Question> questionProperty() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question.set(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(idAnswer.get(), answer.idAnswer.get()) &&
                Objects.equals(textAnswer.get(), answer.textAnswer.get()) &&
                Objects.equals(dateEdit.get(), answer.dateEdit.get()) &&
                Objects.equals(dirImage.get(), answer.dirImage.get()) &&
                Objects.equals(dirAudio.get(), answer.dirAudio.get()) &&
                Objects.equals(dirVideo.get(), answer.dirVideo.get()) &&
                Objects.equals(right.get(), answer.right.get()) &&
                Objects.equals(user.get(), answer.user.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnswer.get(), textAnswer.get(), dateEdit.get(), dirImage.get(), dirAudio.get(), dirVideo.get(), right.get(), user.get());
    }
}
