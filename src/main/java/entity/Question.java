package entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Question implements Serializable {

    private Integer idQuestion;
    private String textQuestion;
    private String themeQuestion;
    private Date dateReg;
    private Byte typeQuestion;
    private String dirImage;
    private String dirAudio;
    private String dirVideo;
    private User user;

    public Question() {}

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public String getThemeQuestion() {
        return themeQuestion;
    }

    public void setThemeQuestion(String themeQuestion) {
        this.themeQuestion = themeQuestion;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public Byte getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(Byte typeQuestion) {
        this.typeQuestion = typeQuestion;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
