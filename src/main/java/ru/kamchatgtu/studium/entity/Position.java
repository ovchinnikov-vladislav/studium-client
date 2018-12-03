package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Position {

    private IntegerProperty idPosition;
    private StringProperty position;
    private IntegerProperty access;

    public Position() {
        idPosition = new SimpleIntegerProperty();
        position = new SimpleStringProperty();
        access = new SimpleIntegerProperty();
    }

    public int getIdPosition() {
        return idPosition.get();
    }

    public IntegerProperty idPositionProperty() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition.set(idPosition);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getAccess() {
        return access.get();
    }

    public IntegerProperty accessProperty() {
        return access;
    }

    public void setAccess(int access) {
        this.access.set(access);
    }

    @Override
    public boolean equals(Object position) {
        if (position == null) return false;
        if (!(position instanceof Position)) return false;

        return ((Position) position).position.equals(this.position) &&
                ((Position) position).access.equals(this.access) &&
                ((Position) position).idPosition.equals(this.idPosition);
    }

    @Override
    public String toString() {
        return position.get();
    }
}
