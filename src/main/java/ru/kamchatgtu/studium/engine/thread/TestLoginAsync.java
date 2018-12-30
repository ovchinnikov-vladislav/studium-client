package ru.kamchatgtu.studium.engine.thread;

import javafx.scene.control.TextField;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class TestLoginAsync extends ErrorTextFieldAsync {

    private RestConnection rest;

    public TestLoginAsync(TextField loginField) {
        super(loginField);
        this.rest = new RestConnection();
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        User user = rest.getRestUser().getUserByLogin(getErrorText().getText());
        return user != null;
    }
}
