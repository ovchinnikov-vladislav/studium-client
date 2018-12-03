package entity;

import java.util.Date;
import java.util.Set;

public class Answer {

    private Integer idAnswer;
    private String textAnswer;
    private Date dateEdit;
    private String dirImage;
    private String dirAudio;
    private String dirVideo;
    private Byte right;
    private User user;
    private Question question;

    public Answer() {}

    public Integer getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public String getDirImage() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public String getDirAudio() {
        return dirAudio;
    }

    public void setDirAudio(String dirAudio) {
        this.dirAudio = dirAudio;
    }

    public String getDirVideo() {
        return dirVideo;
    }

    public void setDirVideo(String dirVideo) {
        this.dirVideo = dirVideo;
    }

    public Byte getRight() {
        return right;
    }

    public void setRight(Byte right) {
        this.right = right;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
