package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Theme implements Serializable {

    private IntegerProperty idTheme;
    private StringProperty theme;

    public Theme() {
        idTheme = new SimpleIntegerProperty();
        theme = new SimpleStringProperty();
    }

    public int getIdTheme() {
        return idTheme.get();
    }

    public void setIdTheme(int idTheme) {
        this.idTheme.set(idTheme);
    }

    public IntegerProperty idThemeProperty() {
        return idTheme;
    }

    public String getTheme() {
        return theme.get();
    }

    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    public StringProperty themeProperty() {
        return theme;
    }

    @Override
    public String toString() {
        return theme.getValue();
    }
}
