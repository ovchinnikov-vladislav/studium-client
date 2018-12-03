package ru.kamchatgtu.studium.engine.thread;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.scene.control.TextField;

public abstract class ErrorTextFieldAsync extends AsyncTask<Void, Void, Boolean> {

    private TextField errorText;

    public ErrorTextFieldAsync(TextField errorText) {
        this.errorText = errorText;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        if (aBoolean)
            errorText.setStyle("-fx-border-color: red");
        else
            errorText.setStyle("-fx-border-color: linear-gradient(from 50% 0% to 50% 100%, rgba(0,0,0,0.2), rgba(0,0,0,0.2))");
    }

    protected TextField getErrorText() {
        return errorText;
    }

    @Override
    public void progressCallback(Void... voids) {

    }
}
