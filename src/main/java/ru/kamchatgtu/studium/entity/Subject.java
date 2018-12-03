package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private IntegerProperty idSubject;
    private StringProperty nameSubject;

    public Subject() {
        idSubject = new SimpleIntegerProperty();
        nameSubject = new SimpleStringProperty();
    }

    public int getIdSubject() {
        return idSubject.get();
    }

    public IntegerProperty idSubjectProperty() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject.set(idSubject);
    }

    public String getNameSubject() {
        return nameSubject.get();
    }

    public StringProperty nameSubjectProperty() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject.set(nameSubject);
    }
}
