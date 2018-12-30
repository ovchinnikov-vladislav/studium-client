package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import ru.kamchatgtu.studium.engine.EditCell;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsersPanelController {

    private RestConnection restConnection;
    private HashMap<User, String> commandsUser;
    private static ObservableList<User> users;
    private static ObservableList<Position> positions;
    private static ObservableList<Group> groups;

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView usersTable;
    @FXML
    private TableColumn fioColumn;
    @FXML
    private TableColumn loginColumn;
    @FXML
    private TableColumn phoneColumn;
    @FXML
    private TableColumn dateRegColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn positionColumn;
    @FXML
    private TableColumn groupColumn;
    @FXML
    private TableColumn resetPass;

    public static void setUsers(ObservableList<User> users) {
        UsersPanelController.users = users;
    }

    // Методы раздела "Пользователи"
    @FXML
    public void fioEditCommit(TableColumn.CellEditEvent<User, String> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setFio(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void loginEditCommit(TableColumn.CellEditEvent<User, String> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setLogin(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void phoneEditCommit(TableColumn.CellEditEvent<User, String> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setPhone(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void emailEditCommit(TableColumn.CellEditEvent<User, String> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setEmail(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void positionEditCommit(TableColumn.CellEditEvent<User, Position> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setPosition(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void groupEditCommit(TableColumn.CellEditEvent<User, Group> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setGroup(t.getNewValue());
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void addUserAction(ActionEvent event) {
        User newUser = new User();
        newUser.setDateAuth(new Date());
        newUser.setDateReg(new Date());
        Group group = groups.get(0);
        if (users.get(0).getLogin() != null && users.get(0).getGroup() != null)
            users.add(0, newUser);
        usersTable.setItems(users);
    }

    @FXML
    public void deleteUserAction(ActionEvent event) {
        ObservableList<User> usersSelect = usersTable.getSelectionModel().getSelectedItems();
        for (User user : usersSelect) {
            commandsUser.put(user, "delete");
        }
        usersTable.getItems().removeAll(usersSelect);
        users.removeAll(usersSelect);
    }

    @FXML
    public void deleteUserKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            ObservableList<User> usersSelect = usersTable.getSelectionModel().getSelectedItems();
            for (User user : usersSelect) {
                commandsUser.put(user, "delete");
            }
            usersTable.getItems().removeAll(usersSelect);
            users.removeAll(usersSelect);
        }
    }

    @FXML
    public void saveUserAction(ActionEvent event) {
        SaveUserTask saveUserTask = new SaveUserTask();
        //  progressBar.progressProperty().unbind();
        //  progressBar.progressProperty().bind(saveUserTask.progressProperty());
        Thread thread = new Thread(saveUserTask);
        thread.start();
    }

    public static void setPositions(ObservableList<Position> positions) {
        UsersPanelController.positions = positions;
    }

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        commandsUser = new HashMap<>();
        usersTable.setItems(users);
        initFio();
        initLogin();
        initEmail();
        initPhone();
        initPosition();
        initGroup();
        initResetPassButton();
        initDate();
        initSearchUser();
    }

    private void initFio() {
        fioColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initLogin() {
        loginColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initPosition() {
        positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, String> param) {
                User user = param.getValue();
                Position position = user.getPosition();
                if (position != null) {
                    return position.positionProperty();
                }
                return null;
            }
        });
        positionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(positions));
    }

    private void initEmail() {
        emailColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initPhone() {
        phoneColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initDate() {
        dateRegColumn.setCellFactory(column -> {
            TableCell<User, Date> cell = new TableCell<User, Date>() {
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
            return cell;
        });
    }

    private void initGroup() {
        groups = restConnection.getRestGroup().getAll();
        groupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Group>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, Group> param) {
                User user = param.getValue();
                Group group = user.getGroup();
                return group.nameGroupProperty();
            }
        });
        groupColumn.setCellFactory(ComboBoxTableCell.forTableColumn(groups));
    }

    private void initResetPassButton() {
        resetPass.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory =
                new Callback<TableColumn<User, String>, TableCell<User, String>>() {
                    @Override
                    public TableCell call(final TableColumn<User, String> param) {
                        final TableCell<User, String> cell = new TableCell<User, String>() {

                            final Button btn = new Button("Сброс");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.getStyleClass().addAll("buttonTable");
                                    btn.setOnAction(event -> {
                                        User user = getTableView().getItems().get(getIndex());
                                        user.setStatus(0);
                                        commandsUser.put(user, "update");
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        resetPass.setCellFactory(cellFactory);
    }

    private void initSearchUser() {
        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));
    }

    private class SaveUserTask extends Task<ObservableList<User>> {

        @Override
        protected ObservableList<User> call() {
            ObservableList<User> users = FXCollections.observableArrayList();
            int i = 0;
            int count = commandsUser.size();
            for (Map.Entry<User, String> command : commandsUser.entrySet()) {
                User user = command.getKey();
                String com = command.getValue();
                if ("update".equals(com))
                    restConnection.getRestUser().update(user);
                else if ("insert".equals(com)) {
                    user.setPassword(SecurityAES.encryptPass(SecurityAES.getRandomPass()));
                    restConnection.getRestUser().add(user);
                } else if ("delete".equals(com)) {
                    restConnection.getRestUser().remove(user);
                }
                this.updateProgress(++i, count);
            }
            return users;
        }
    }

    private class SearchTask extends AsyncTask<Void, Void, ObservableList<User>> {

        private String newValue;

        private SearchTask(String value) {
            this.newValue = value;
        }


        @Override
        public void onPreExecute() {

        }

        @Override
        public ObservableList<User> doInBackground(Void... voids) {
            User user = new User();
            user.setFio(newValue);
            user.setLogin(newValue);
            user.setEmail(newValue);
            user.setPhone(newValue);
            Position position = new Position();
            position.setPosition(newValue);
            user.setPosition(position);
            Group group = new Group();
            group.setNameGroup(newValue);
            user.setGroup(group);
            return restConnection.getRestUser().search(user);
        }

        @Override
        public void onPostExecute(ObservableList<User> aUsers) {
            users.setAll(aUsers);
            usersTable.setItems(users);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
    //---------------------------------------------
}
