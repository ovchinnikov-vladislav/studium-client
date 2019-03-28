package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Collection;
import java.util.Objects;

public class Theme {

    private IntegerProperty idTheme;
    private StringProperty themeText;
    @JsonIgnore
    private ObservableList<Question> questions;

    public Theme() {
        idTheme = new SimpleIntegerProperty();
        themeText = new SimpleStringProperty();
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

    public String getThemeText() {
        return themeText.get();
    }

    public void setThemeText(String themeText) {
        this.themeText.set(themeText);
    }

    public StringProperty themeProperty() {
        return themeText;
    }

    public ObservableList<Question> getQuestions() {
        RestConnection rest = new RestConnection();
        questions = rest.getRestQuestion().getQuestionsByTheme(getIdTheme());
        return questions;
    }

    @Override
    public String toString() {
        return themeText.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(idTheme.get(), theme.idTheme.get()) &&
                Objects.equals(themeText.get(), theme.themeText.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTheme.get(), themeText.get());
    }
}
