package ru.kamchatgtu.studium.entity.user;

import ru.kamchatgtu.studium.restclient.RestConnection;

public class Teacher extends User {
    private RestConnection restConnection;

    public Teacher() {
        super();
        restConnection = new RestConnection();
    }

}
