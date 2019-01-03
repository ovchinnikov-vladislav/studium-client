package ru.kamchatgtu.studium.entity.user;

import ru.kamchatgtu.studium.restclient.RestConnection;

public class Student extends User {
    private RestConnection restConnection;

    public Student() {
        super();
        restConnection = new RestConnection();
    }
}
