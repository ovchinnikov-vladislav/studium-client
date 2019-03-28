package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.ResultTestWindowController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.result.ResultTestWindow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsPanelController {

    @FXML
    private GridPane resultsPane;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TableView<ResultTest> resultTable;
    @FXML
    private TableColumn<ResultTest, String> idColumn;
    @FXML
    private TableColumn<ResultTest, String> fioResultColumn;
    @FXML
    private TableColumn<ResultTest, String> nameTestResultColumn;
    @FXML
    private TableColumn<ResultTest, Date> dateBeginColumn;
    @FXML
    private TableColumn<ResultTest, Date> dateEndColumn;
    @FXML
    private TableColumn<ResultTest, Number> markColumn;

    @FXML
    public void initialize() {
        initIdColumn();
        initFioResultColumn();
        initNameResultColumn();
        initDateBeginColumn();
        initDateEndColumn();
        initMarkColumn();
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
        fioResultColumn.setCellValueFactory(param -> param.getValue().getUser().fioProperty());
    }

    private void initNameResultColumn() {
        nameTestResultColumn.setCellValueFactory(param -> param.getValue().getTest().testNameProperty());
    }

    private void initDateBeginColumn() {
        initTime(dateBeginColumn);
        dateBeginColumn.setCellValueFactory(param -> param.getValue().dateBeginProperty());
    }

    private void initDateEndColumn() {
        initTime(dateEndColumn);
        dateEndColumn.setCellValueFactory(param -> param.getValue().dateEndProperty());
    }

    private void initMarkColumn() {
        markColumn.setCellValueFactory(param -> param.getValue().markProperty());
    }

    private void initTime(TableColumn<ResultTest, Date> time) {
        time.setCellFactory(column -> new TableCell<ResultTest, Date>() {
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

    private class ResultsAsync extends AsyncTask<Void, Void, ObservableList<ResultTest>> {
        @Override
        public void onPreExecute() {
            progressIndicator.setVisible(true);
            resultTable.setVisible(false);
        }

        @Override
        public ObservableList<ResultTest> doInBackground(Void... voids) {
            int access = SecurityAES.USER_LOGIN.getGroup().getRole().getAccess();
            if (access == 2) {
                return new RestConnection().getRestResultTest().getByUserTests(SecurityAES.USER_LOGIN);
            } else if (access == 3) {
                return new RestConnection().getRestResultTest().getByUser(SecurityAES.USER_LOGIN);
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
}
