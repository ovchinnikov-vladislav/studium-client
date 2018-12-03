package entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

public class Subject {

    private Integer idSubject;
    private String nameSubject;

    public Subject() {}

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }
}
