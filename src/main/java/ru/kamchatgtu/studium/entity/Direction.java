package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import java.util.Objects;

public class Direction {

    private IntegerProperty idDirection;
    private StringProperty directionName;
    private ObjectProperty<Faculty> faculty;
    @JsonIgnore
    private BooleanProperty inSubject;

    public Direction() {
        idDirection = new SimpleIntegerProperty();
        directionName = new SimpleStringProperty();
        faculty = new SimpleObjectProperty<>();
        inSubject = new SimpleBooleanProperty();
    }

    public int getIdDirection() {
        return idDirection.get();
    }

    public IntegerProperty idDirectionProperty() {
        return idDirection;
    }

    public void setIdDirection(int idDirection) {
        this.idDirection.set(idDirection);
    }

    public String getDirectionName() {
        return directionName.get();
    }

    public StringProperty directionNameProperty() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName.set(directionName);
    }

    public Faculty getFaculty() {
        return faculty.get();
    }

    public ObjectProperty<Faculty> facultyProperty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty.set(faculty);
    }

    public boolean isInSubject() {
        return inSubject.get();
    }

    public void setInSubject(boolean inSubject) {
        this.inSubject.set(inSubject);
    }

    public BooleanProperty inSubjectProperty() {
        return inSubject;
    }

    @Override
    public String toString() {
        return directionName.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(idDirection.get(), direction.idDirection.get()) &&
                Objects.equals(directionName.get(), direction.directionName.get()) &&
                Objects.equals(inSubject.get(), direction.inSubject.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDirection.get(), directionName.get(), inSubject.get());
    }
}
