package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Objects;

public class Theme {

    private IntegerProperty idTheme;
    private StringProperty theme;
    @JsonIgnore
    private ObservableList<Question> questions;

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

    public ObservableList<Question> getQuestions() {
        RestConnection rest = new RestConnection();
        questions = rest.getRestQuestion().getQuestionsByTheme(getIdTheme());
        return questions;
    }

    @Override
    public String toString() {
        return theme.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme1 = (Theme) o;
        return Objects.equals(idTheme.get(), theme1.idTheme.get()) &&
                Objects.equals(theme.get(), theme1.theme.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTheme.get(), theme.get());
    }
}
