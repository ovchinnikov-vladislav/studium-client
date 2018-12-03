package ru.kamchatgtu.studium.entity.user;

import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class Administrator extends User {

    private RestConnection restConnection;

    public Administrator() {
        super();
        restConnection = new RestConnection();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(restConnection.getRestPosition().get(1));
    }
}
