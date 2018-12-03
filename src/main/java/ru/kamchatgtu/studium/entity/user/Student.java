package ru.kamchatgtu.studium.entity.user;

import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class Student extends User {
    private RestConnection restConnection;

    public Student() {
        super();
        restConnection = new RestConnection();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(restConnection.getRestPosition().get(3));
    }
}
