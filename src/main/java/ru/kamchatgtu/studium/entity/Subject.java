package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.Set;

public class Subject {

    private IntegerProperty idSubject;
    private StringProperty nameSubject;
    private Set<Group> groups;

    public Subject() {
        idSubject = new SimpleIntegerProperty();
        nameSubject = new SimpleStringProperty();
        groups = new HashSet<Group>();
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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return nameSubject.get();
    }
}
