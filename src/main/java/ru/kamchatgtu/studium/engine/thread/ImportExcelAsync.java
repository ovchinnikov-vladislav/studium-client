package ru.kamchatgtu.studium.engine.thread;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.message.Message;

import java.io.File;

public class ImportExcelAsync extends AsyncTask<Void, Void, Boolean> {

    private RestConnection rest;
    private ImportExcelTask task;

    private Label taskLabel;
    private ProgressBar progressBar;
    private ObservableList<Theme> themes;
    private ComboBox<Theme> themeBox;
    private File file;

    public ImportExcelAsync(Label taskLabel, ProgressBar progressBar, ObservableList<Theme> themes, ComboBox<Theme> themeBox, File file) {
        this.taskLabel = taskLabel;
        this.progressBar = progressBar;
        this.themes = themes;
        this.themeBox = themeBox;
        this.file = file;
        this.rest = new RestConnection();
    }

    @Override
    public void onPreExecute() {
        task = new ImportExcelTask(file);
        taskLabel.setText(" - импортирование вопросов");
        progressBar.setProgress(0);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            themes.setAll(rest.getRestTheme().getAll());
            themeBox.setItems(themes);
            Message message = new Message(Alert.AlertType.INFORMATION);
            message.setHeaderText("Import");
            message.setContentText("Вопросы из файла \"" + file.getAbsolutePath() + "\" были успешно импортированы.");
            message.showAndWait();
            taskLabel.setText("");
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
        }
    }

    @Override
    public void progressCallback(Void... voids) {

    }
}
