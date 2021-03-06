package ru.kamchatgtu.studium.restclient;

import javafx.collections.ObservableList;

public interface AbstractRest<T> {
    T add(T t);

    T update(T t);

    T remove(T t);

    T get(int id);

    ObservableList<T> getAll();
}
