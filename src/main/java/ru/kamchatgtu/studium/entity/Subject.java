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
    private StringProperty subjectName;
    private Set<Direction> directions;
    private Set<User> users;
    @JsonIgnore
    private ObservableList<Test> tests;

    public Subject() {
        idSubject = new SimpleIntegerProperty();
        subjectName = new SimpleStringProperty();
        directions = new HashSet<>();
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

    public String getSubjectName() {
        return subjectName.get();
    }

    public StringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    public synchronized Set<Direction> getDirections() {
        return directions;
    }

    public void setDirections(Set<Direction> directions) {
        this.directions = directions;
    }

    public ObservableList<Test> getTests() {
        return tests;
    }

    public void setTests(ObservableList<Test> tests) {
        this.tests = tests;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return subjectName.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(idSubject.get(), subject.idSubject.get()) &&
                Objects.equals(subjectName.get(), subject.subjectName.get()) &&
                Objects.equals(directions, subject.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubject.get(), subjectName.get());
    }
}
