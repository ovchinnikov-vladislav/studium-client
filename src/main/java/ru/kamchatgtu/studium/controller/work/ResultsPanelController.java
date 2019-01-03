package ru.kamchatgtu.studium.controller.work;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsPanelController {

    @FXML
    private GridPane resultsPane;

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
    private TableColumn<ResultTest, String> markColumn;

    @FXML
    public void initialize() {
        initIdColumn();
        initFioResultColumn();
        initNameResultColumn();
        initDateBeginColumn();
        initDateEndColumn();
        initMarkColumn();
        resultsPane.visibleProperty().addListener(observable -> {
            int access = SecurityAES.USER_LOGIN.getGroup().getPosition().getAccess();
            if (access == 2) {
                ObservableList<ResultTest> resultTests = new RestConnection().getRestResultTest().getByUserTests(SecurityAES.USER_LOGIN);
                resultTable.setItems(resultTests);
            } else if (access == 3) {
                ObservableList<ResultTest> resultTests = new RestConnection().getRestResultTest().getByUser(SecurityAES.USER_LOGIN);
                resultTable.setItems(resultTests);
            }
        });
    }

    private void initIdColumn() {
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(resultTable.getItems().indexOf(param.getValue()) + 1 + ""));
    }

    private void initFioResultColumn() {
        fioResultColumn.setCellValueFactory(param -> param.getValue().getUser().fioProperty());
    }

    private void initNameResultColumn() {
        nameTestResultColumn.setCellValueFactory(param -> param.getValue().getTest().nameTestProperty());
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
        markColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().markProperty().get() + ""));
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
}
