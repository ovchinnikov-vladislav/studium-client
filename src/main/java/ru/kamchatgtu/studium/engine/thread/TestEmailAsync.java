package ru.kamchatgtu.studium.engine.thread;

import javafx.scene.control.TextField;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class TestEmailAsync extends ErrorTextFieldAsync {

    private RestConnection rest;

    public TestEmailAsync(TextField emailField) {
        super(emailField);
        this.rest = new RestConnection();
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        User user = rest.getRestUser().getUserByEmail(getErrorText().getText());
        return user != null;
    }
}
