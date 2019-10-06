package ru.kamchatgtu.studium.controller.work.system_info;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Log;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsPanelController {

    private ObservableList<Log> logs;

    @FXML
    private GridPane logsPane;
    @FXML
    private TextField searchLogsTextField;
    @FXML
    private TableView<Log> logsTable;
    @FXML
    private TableColumn<Log, String> textQueryColumn;
    @FXML
    private TableColumn<Log, Date> dateQueryColumn;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        initDateQuery();
        initTextQuery();
        initSearchLogTextField();
        initDownloadLogsAsync();
        logsPane.visibleProperty().addListener(observable -> {
            if (logsPane.isVisible())
                initDownloadLogsAsync();
        });
    }

    private void initSearchLogTextField() {
        searchLogsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Log log = new Log();
            log.setCodeQuery(newValue);
            log.setTypeQuery(newValue);
            log.setTextQuery(newValue);
            log.setTableName(newValue);
            SearchLogsAsync searchLogsAsync = new SearchLogsAsync(log);
            searchLogsAsync.execute();
        });
    }

    private void initTextQuery() {
        textQueryColumn.setCellFactory(param -> {
            TableCell<Log, String> cell = new TableCell<>();
            Text text = new Text();
            text.setStyle("-fx-fill: -fx-text-background-color;");
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    private void initDateQuery() {
        initTime(dateQueryColumn);
    }

    private void initTime(TableColumn<Log, Date> time) {
        time.setCellFactory(column -> new TableCell<Log, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("Дата: dd-MM-yyyy, время: HH:mm:ss");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });
    }

    private void initDownloadLogsAsync() {
        DownloadLogsAsync logsAsync = new DownloadLogsAsync();
        logsAsync.execute();
    }

    private class DownloadLogsAsync extends AsyncTask<Void, Void, Boolean> {
        @Override
        public void onPreExecute() {
            progressIndicator.setVisible(true);
            logsTable.setDisable(true);
            logsTable.setItems(null);
            searchLogsTextField.setDisable(true);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            logs = new RestConnection().getRestLog().getAll();
            if (logs != null)
                return true;
            return false;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            progressIndicator.setVisible(false);
            logsTable.setItems(logs);
            logsTable.setDisable(false);
            searchLogsTextField.setDisable(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class SearchLogsAsync extends AsyncTask<Void, Void, Boolean> {

        private Log log;

        public SearchLogsAsync(Log log) {
            this.log = log;
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public Boolean doInBackground(Void... voids) {
            if (log.getTextQuery().length() > 2)
                logs = new RestConnection().getRestLog().search(log);
            else
                logs = new RestConnection().getRestLog().getAll();
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            logsTable.setItems(logs);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
