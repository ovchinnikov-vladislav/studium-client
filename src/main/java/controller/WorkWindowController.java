package controller;

import engine.RestConnection;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import engine.Database;
import model.Group;
import model.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;


public class WorkWindowController {

    @FXML private TableView usersTable;
    @FXML private TableColumn fioColumn;
    @FXML private TableColumn loginColumn;
    @FXML private TableColumn phoneColumn;
    @FXML private TableColumn dateRegColumn;
    @FXML private TableColumn emailColumn;
    @FXML private TableColumn positionColumn;
    @FXML private TableColumn groupColumn;
    @FXML private TableColumn accessColumn;

    @FXML public void initialize() {
        User[] users = RestConnection.getInstance().getUsers();
        ObservableList<User> observableList = new SimpleListProperty<>();
        for (User user : users) {
            observableList.add(user);
        }
        usersTable.setItems(observableList);
        initPosition();
        initGroup();
        initColumnWidth();
    }

    @FXML public void fioEditCommit(TableColumn.CellEditEvent<User, String> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setFio(t.getNewValue());
        int number = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(number);
        if ((user.getFio() == null || user.getFio().isEmpty())
                && (user.getEmail() == null || user.getEmail().isEmpty())
                && (user.getLogin() == null || user.getLogin().isEmpty())) {
            usersTable.getItems().remove(number);
        }
    }

    @FXML public void loginEditCommit(TableColumn.CellEditEvent<User, String> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setLogin(t.getNewValue());
    }

    @FXML public void phoneEditCommit(TableColumn.CellEditEvent<User, String> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone(t.getNewValue());
    }

    @FXML public void emailEditCommit(TableColumn.CellEditEvent<User, String> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
    }

    @FXML public void positionEditCommit(TableColumn.CellEditEvent<User, String> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setPosition(t.getNewValue());
    }

    @FXML public void groupEditCommit(TableColumn.CellEditEvent<User, String> t) {
     //   t.getTableView().getItems().get(t.getTablePosition().getRow()).setGroup(t.getNewValue());
    }

    @FXML public void addUserAction(ActionEvent event) {
        usersTable.getItems().add(0, new User());
        usersTable.edit(0, fioColumn);
    }

    @FXML public void deleteUserAction(ActionEvent event) {
        ObservableList<User> usersSelect = usersTable.getSelectionModel().getSelectedItems();
        usersTable.getItems().removeAll(usersSelect);
    }

    @FXML public void deleteUserKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            ObservableList<User> usersSelect = usersTable.getSelectionModel().getSelectedItems();
            usersTable.getItems().removeAll(usersSelect);
        }
    }

    @FXML public void saveUserAction(ActionEvent event) {
        // TODO:
    }

    private void initPosition() {
        ObservableList<String> positionList = Database.getPositions();
        positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, String> param) {
                User user = param.getValue();
                String position = user.getPosition();
                return new SimpleStringProperty(position);
            }
        });
        positionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(positionList));
    }

    private void initGroup() {
        ObservableList<String> groupList = Database.getGroups();
        groupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, String> param) {
                User user = param.getValue();
                Group group = user.getGroup();
                return new SimpleObjectProperty(group);
            }
        });
        groupColumn.setCellFactory(ComboBoxTableCell.forTableColumn(groupList));
    }

    private void initColumnWidth() {
        fioColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.15));
        loginColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.10));
        emailColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.24));
        phoneColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.14));
        groupColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.14));
        dateRegColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.09));
        positionColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.09));
        accessColumn.prefWidthProperty().bind(usersTable.widthProperty().multiply(0.05));
    }
}
