package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

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
    public String toString() {
        return position.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position1 = (Position) o;
        return Objects.equals(idPosition.get(), position1.idPosition.get()) &&
                Objects.equals(position.get(), position1.position.get()) &&
                Objects.equals(access.get(), position1.access.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPosition.get(), position.get(), access.get());
    }
}
