package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Question implements Cloneable {

    private IntegerProperty idQuestion;
    private StringProperty questionText;
    private ObjectProperty<Theme> theme;
    private ObjectProperty<Date> dateEdit;
    private IntegerProperty questionType;
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
        questionText = new SimpleStringProperty();
        theme = new SimpleObjectProperty<>();
        dateEdit = new SimpleObjectProperty<>();
        questionType = new SimpleIntegerProperty();
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

    public String getQuestionText() {
        return questionText.get();
    }

    public StringProperty questionTextProperty() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText.set(questionText);
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

    public Date getDateEdit() {
        return dateEdit.get();
    }

    public ObjectProperty<Date> dateEditProperty() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit.set(dateEdit);
    }

    public int getQuestionType() {
        return questionType.get();
    }

    public IntegerProperty questionTypeProperty() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType.set(questionType);
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
        return questionText.get();
    }

    @Override
    public Object clone() {
        Question question = new Question();
        question.setQuestionType(getQuestionType());
        question.setTheme(getTheme());
        question.setDateEdit(getDateEdit());
        question.setDirAudio(getDirAudio());
        question.setDirImage(getDirImage());
        question.setDirVideo(getDirVideo());
        question.setIdQuestion(getIdQuestion());
        question.setQuestionText(getQuestionText());
        question.setUser(getUser());
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(idQuestion.get(), question.idQuestion.get()) &&
                Objects.equals(questionText.get(), question.questionText.get()) &&
                Objects.equals(theme.get(), question.theme.get()) &&
                Objects.equals(dateEdit.get(), question.dateEdit.get()) &&
                Objects.equals(questionType.get(), question.questionType.get()) &&
                Objects.equals(dirImage.get(), question.dirImage.get()) &&
                Objects.equals(dirAudio.get(), question.dirAudio.get()) &&
                Objects.equals(dirVideo.get(), question.dirVideo.get()) &&
                Objects.equals(user.get(), question.user.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion.get(), questionText.get(), theme.get(), dateEdit.get(), questionType.get(), dirImage.get(), dirAudio.get(), dirVideo.get(), user.get());
    }
}
