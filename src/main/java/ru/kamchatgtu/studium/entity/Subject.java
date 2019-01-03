package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Subject {

    private IntegerProperty idSubject;
    private StringProperty nameSubject;
    private Set<Group> groups;
    @JsonIgnore
    private ObservableList<Test> tests;

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

    public ObservableList<Test> getTests() {
        return tests;
    }

    public void setTests(ObservableList<Test> tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return nameSubject.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(idSubject.get(), subject.idSubject.get()) &&
                Objects.equals(nameSubject.get(), subject.nameSubject.get()) &&
                Objects.equals(groups, subject.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubject.get(), nameSubject.get());
    }
}
