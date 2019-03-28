package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.test.CreateTestWindowController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.work.test.CreateTestWindow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTestPanelController {

    private RestConnection rest;
    private ObservableList<Test> tests;

    @FXML
    private GridPane createTestsPanel;

    @FXML
    private GridPane buttonsPane;

    @FXML
    private TableView<Test> testTable;
    @FXML
    private TableColumn<Test, String> idColumn;
    @FXML
    private TableColumn<Test, Date> dateEditColumn;
    @FXML
    private TableColumn<Test, Date> timerTestColumn;
    @FXML
    private TableColumn<Test, String> userColumn;
    @FXML
    private TableColumn<Test, String> subjectColumn;
    @FXML
    private TableColumn<Test, String> countQuesColumn;

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        rest = new RestConnection();
        initTestsTable();
        initId();
        initDateEditColumn();
        initTimerTestColumn();
        initUserColumn();
        initSubjectColumn();
        initCountQuesColumn();
        createTestsPanel.visibleProperty().addListener(observable -> {
            TestAsync testAsync = new TestAsync();
            testAsync.execute();
        });
    }

    private void initTestsTable() {
        testTable.setRowFactory(tv -> {
            TableRow<Test> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editTest(row.getItem());
                }
            });
            return row;
        });
        testTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (testTable.getSelectionModel().getSelectedIndex() != -1) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
    }

    @FXML
    public void addTestAction(ActionEvent event) {
        try {
            CreateTestWindowController.setIsAdd(true);
            CreateTestWindowController.setSelectedTest(null);
            Stage stage = CreateTestWindow.getStage();
            stage.initOwner(buttonsPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tests = rest.getRestTest().getTestsByUser(SecurityAES.USER_LOGIN.getIdUser());
            testTable.setItems(tests);
            testTable.getSelectionModel().select(CreateTestWindowController.getSelectedTest());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void editTestAction(ActionEvent event) {
        editTest(testTable.getSelectionModel().getSelectedItem());
    }

    private void editTest(Test test) {
        try {
            CreateTestWindowController.setIsAdd(false);
            CreateTestWindowController.setSelectedTest(test);
            Stage stage = CreateTestWindow.getStage();
            stage.initOwner(buttonsPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tests = rest.getRestTest().getTestsByUser(SecurityAES.USER_LOGIN.getIdUser());
            testTable.setItems(tests);
            testTable.getSelectionModel().select(CreateTestWindowController.getSelectedTest());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void deleteTestAction(ActionEvent event) {
        rest.getRestTest().remove(testTable.getSelectionModel().getSelectedItem());
        tests = rest.getRestTest().getAll();
        testTable.setItems(tests);
    }

    private void initId() {
        idColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(testTable.getItems().indexOf(param.getValue()) + 1));
    }

    private void initDateEditColumn() {
        dateEditColumn.setCellFactory(column ->
        {
            return new TableCell<Test, Date>() {
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
            };
        });
    }

    private void initTimerTestColumn() {
        timerTestColumn.setCellFactory(column -> {
            return new TableCell<Test, Date>() {
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
            };
        });
    }

    private void initUserColumn() {
        userColumn.setCellValueFactory(param -> param.getValue().getUser().fioProperty());
    }

    private void initSubjectColumn() {
        subjectColumn.setCellValueFactory(param -> param.getValue().getSubject().subjectNameProperty());
    }

    private void initCountQuesColumn() {
        countQuesColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getQuestions().size() + ""));
    }

    private class TestAsync extends AsyncTask<Void, Void, ObservableList<Test>> {
        @Override
        public void onPreExecute() {

        }

        @Override
        public ObservableList<Test> doInBackground(Void... voids) {
            return rest.getRestTest().getTestsByUser(SecurityAES.USER_LOGIN.getIdUser());
        }

        @Override
        public void onPostExecute(ObservableList<Test> tests) {
            testTable.setItems(tests);
            testTable.getSelectionModel().clearSelection();
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
