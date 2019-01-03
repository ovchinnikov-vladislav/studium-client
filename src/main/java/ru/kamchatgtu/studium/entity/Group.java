package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

public class Group {

    private IntegerProperty idGroup;
    private StringProperty nameGroup;
    private StringProperty direction;
    private ObjectProperty<Position> position;
    @JsonIgnore
    private BooleanProperty inSubject;

    public Group() {
        this.idGroup = new SimpleIntegerProperty();
        this.nameGroup = new SimpleStringProperty();
        this.direction = new SimpleStringProperty();
        this.position = new SimpleObjectProperty<>();
        inSubject = new SimpleBooleanProperty();
    }

    public int getIdGroup() {
        return idGroup.get();
    }

    public IntegerProperty idGroupProperty() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup.set(idGroup);
    }

    public String getNameGroup() {
        return nameGroup.get();
    }

    public StringProperty nameGroupProperty() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup.set(nameGroup);
    }

    public String getDirection() {
        return direction.get();
    }

    public StringProperty directionProperty() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction.set(direction);
    }

    public Position getPosition() {
        return position.get();
    }

    public void setPosition(Position position) {
        this.position.set(position);
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
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
        return nameGroup.get();
    }

    @Override
    public boolean equals(Object group) {
        if (group == null) return false;
        if (!(group instanceof Group)) return false;

        return ((Group) group).idGroup.get() == (this.idGroup.get()) &&
                ((Group) group).nameGroup.get().equals(this.nameGroup.get()) &&
                ((Group) group).direction.get().equals(this.direction.get());
    }
}
