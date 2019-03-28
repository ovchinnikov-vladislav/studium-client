package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class Faculty {

    private IntegerProperty idFaculty;
    private StringProperty facultyName;

    public Faculty() {
        idFaculty = new SimpleIntegerProperty();
        facultyName = new SimpleStringProperty();
    }

    public int getIdFaculty() {
        return idFaculty.get();
    }

    public IntegerProperty idFacultyProperty() {
        return idFaculty;
    }

    public void setIdFaculty(int idFaculty) {
        this.idFaculty.set(idFaculty);
    }

    public String getFacultyName() {
        return facultyName.get();
    }

    public StringProperty facultyNameProperty() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName.set(facultyName);
    }

    @Override
    public String toString() {
        return facultyName.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(idFaculty.get(), faculty.idFaculty.get()) &&
                Objects.equals(facultyName.get(), faculty.facultyName.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFaculty.get(), facultyName.get());
    }
}
