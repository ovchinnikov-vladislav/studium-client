package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.ResultTestWindowController;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.work.test.ResultTestWindow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsPanelController {

    @FXML
    private GridPane resultsPane;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField searchResultsTextField;
    @FXML
    private TableView<ResultTest> resultTable;
    @FXML
    private TableColumn<ResultTest, String> idColumn;
    @FXML
    private TableColumn<ResultTest, String> fioResultColumn;
    @FXML
    private TableColumn<ResultTest, String> nameTestResultColumn;
    @FXML
    private TableColumn<ResultTest, Date> dateColumn;
    @FXML
    private TableColumn<ResultTest, Date> timeColumn;
    @FXML
    private TableColumn<ResultTest, Number> markColumn;

    @FXML
    public void initialize() {
        initIdColumn();
        initFioResultColumn();
        initNameResultColumn();
        initDateColumn();
        initTimeColumn();
        initMarkColumn();
        initSearchResults();
        resultsPane.visibleProperty().addListener(observable -> {
            ResultsAsync resultsAsync = new ResultsAsync();
            resultsAsync.execute();
        });
        resultTable.setRowFactory(tv -> {
            TableRow<ResultTest> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ResultTest rowData = row.getItem();
                    openResult(rowData);
                }
            });
            return row;
        });
    }

    private void initSearchResults() {
        searchResultsTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));
    }

    private void openResult(ResultTest row) {
        try {
            ResultTestWindowController.setResultTest(row);
            Stage stage = ResultTestWindow.getStage();
            stage.initOwner(resultsPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void initIdColumn() {
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(resultTable.getItems().indexOf(param.getValue()) + 1 + ""));
    }

    private void initFioResultColumn() {
        if (Security.USER_LOGIN.getRole().getAccess() != 3) {
            fioResultColumn.setCellValueFactory(param -> param.getValue().getUser().fioProperty());
        } else {
            fioResultColumn.setVisible(false);
        }
    }

    private void initNameResultColumn() {
        nameTestResultColumn.setCellValueFactory(param -> param.getValue().getTest().testNameProperty());
    }

    private void initDateColumn() {
        initDate(dateColumn);
        dateColumn.setCellValueFactory(param -> param.getValue().dateBeginProperty());
    }

    private void initTimeColumn() {
        initTime(timeColumn);
        timeColumn.setCellValueFactory(param -> {
            Date newDate = new Date();
            newDate.setHours(param.getValue().dateEndProperty().get().getHours()-param.getValue().dateBeginProperty().get().getHours());
            newDate.setMinutes(param.getValue().dateEndProperty().get().getMinutes()-param.getValue().dateBeginProperty().get().getMinutes());
            newDate.setSeconds(param.getValue().dateEndProperty().get().getSeconds()-param.getValue().dateBeginProperty().get().getSeconds());
            return new SimpleObjectProperty<>(newDate);
        }
        );
    }

    private void initMarkColumn() {
        markColumn.setCellValueFactory(param -> param.getValue().markProperty());
    }

    private void initDate(TableColumn<ResultTest, Date> time) {
        time.setCellFactory(column -> new TableCell<ResultTest, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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
    private void initTime(TableColumn<ResultTest, Date> time) {
        time.setCellFactory(column -> new TableCell<ResultTest, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

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
    private class ResultsAsync extends AsyncTask<Void, Void, ObservableList<ResultTest>> {
        @Override
        public void onPreExecute() {
            progressIndicator.setVisible(true);
            resultTable.setVisible(false);
        }

        @Override
        public ObservableList<ResultTest> doInBackground(Void... voids) {
            int access = Security.USER_LOGIN.getRole().getAccess();
            if (access == 2) {
                return new RestConnection().getRestResultTest().getByUserTests(Security.USER_LOGIN.getIdUser());
            } else if (access == 3) {
                return new RestConnection().getRestResultTest().getByUser(Security.USER_LOGIN.getIdUser());
            }
            return null;
        }

        @Override
        public void onPostExecute(ObservableList<ResultTest> resultTests) {
            progressIndicator.setVisible(false);
            resultTable.setVisible(true);
            resultTable.setItems(resultTests);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class SearchTask extends AsyncTask<Void, Void, ObservableList<ResultTest>> {

        private String newValue;

        private SearchTask(String value) {
            this.newValue = value;
        }


        @Override
        public void onPreExecute() {

        }

        @Override
        public ObservableList<ResultTest> doInBackground(Void... voids) {
            if (newValue != null) {
                ResultTest resultTest = new ResultTest();
                User user = new User();
                user.setFio(newValue);
                resultTest.setUser(user);
                Test test = new Test();
                test.setUser(Security.USER_LOGIN);
                test.setTestName(newValue);
                resultTest.setTest(test);
                try {
                    resultTest.setMark(Float.parseFloat(newValue));
                } catch (NumberFormatException exc) {
                    exc.printStackTrace();
                }
                int access = Security.USER_LOGIN.getGroup().getRole().getAccess();
                if (access == 2) {
                    return new RestConnection().getRestResultTest().searchByUserTests(resultTest);
                } else if (access == 3) {
                    resultTest.setUser(Security.USER_LOGIN);
                    return new RestConnection().getRestResultTest().searchByUser(resultTest);
                }
            } else {
                int access = Security.USER_LOGIN.getGroup().getRole().getAccess();
                if (access == 2) {
                    return new RestConnection().getRestResultTest().getByUserTests(Security.USER_LOGIN.getIdUser());
                } else if (access == 3) {
                    return new RestConnection().getRestResultTest().getByUser(Security.USER_LOGIN.getIdUser());
                }
            }
            return null;
        }

        @Override
        public void onPostExecute(ObservableList<ResultTest> resultTests) {
            resultTable.setItems(resultTests);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
