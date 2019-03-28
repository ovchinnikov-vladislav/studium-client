package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import java.util.Date;
import java.util.Objects;

public class Answer {

    private IntegerProperty idAnswer;
    private StringProperty answerText;
    private ObjectProperty<Date> dateEdit;
    private StringProperty dirImage;
    private StringProperty dirAudio;
    private StringProperty dirVideo;
    private BooleanProperty correct;
    private ObjectProperty<User> user;
    private ObjectProperty<Question> question;

    @JsonIgnore
    private BooleanProperty mark;

    public Answer() {
        idAnswer = new SimpleIntegerProperty();
        answerText = new SimpleStringProperty();
        dateEdit = new SimpleObjectProperty<>();
        dirImage = new SimpleStringProperty();
        dirAudio = new SimpleStringProperty();
        dirVideo = new SimpleStringProperty();
        correct = new SimpleBooleanProperty();
        question = new SimpleObjectProperty<>();
        user = new SimpleObjectProperty<>();
        mark = new SimpleBooleanProperty();
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

    public String getAnswerText() {
        return answerText.get();
    }

    public StringProperty answerTextProperty() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText.set(answerText);
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

    public boolean isCorrect() {
        return correct.get();
    }

    public BooleanProperty correctProperty() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct.set(correct);
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

    public boolean isMark() {
        return mark.get();
    }

    public BooleanProperty markProperty() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark.set(mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(idAnswer.get(), answer.idAnswer.get()) &&
                Objects.equals(answerText.get(), answer.answerText.get()) &&
                Objects.equals(dateEdit.get(), answer.dateEdit.get()) &&
                Objects.equals(dirImage.get(), answer.dirImage.get()) &&
                Objects.equals(dirAudio.get(), answer.dirAudio.get()) &&
                Objects.equals(dirVideo.get(), answer.dirVideo.get()) &&
                Objects.equals(correct.get(), answer.correct.get()) &&
                Objects.equals(user.get(), answer.user.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnswer.get(), answerText.get(), dateEdit.get(), dirImage.get(), dirAudio.get(), dirVideo.get(), correct.get(), user.get());
    }
}
