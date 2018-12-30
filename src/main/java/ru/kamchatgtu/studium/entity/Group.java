package ru.kamchatgtu.studium.entity;

import javafx.beans.property.*;

public class Group {

    private IntegerProperty idGroup;
    private StringProperty nameGroup;
    private StringProperty direction;
    private ObjectProperty<Position> position;

    public Group() {
        this.idGroup = new SimpleIntegerProperty();
        this.nameGroup = new SimpleStringProperty();
        this.direction = new SimpleStringProperty();
        this.position = new SimpleObjectProperty<>();
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

    @Override
    public String toString() {
        return nameGroup.get();
    }

    @Override
    public boolean equals(Object group) {
        if (group == null) return false;
        if (!(group instanceof Group)) return false;

        return ((Group) group).idGroup.equals(this.idGroup) &&
                ((Group) group).nameGroup.equals(this.nameGroup) &&
                ((Group) group).direction.equals(this.direction);
    }
}
