package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import ru.kamchatgtu.studium.engine.EditCell;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Role;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsersPanelController {

    private RestConnection restConnection;
    private HashMap<User, String> commandsUser;
    private static ObservableList<User> users;
    private static ObservableList<Role> roles;
    private static ObservableList<Direction> directions;
    private static ObservableList<Group> groups;
    private static ProgressBar progressBar;
    private static Label taskLabel;

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn idColumn;
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
    private TableColumn directionColumn;
    @FXML
    private TableColumn groupColumn;

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
    public void positionEditCommit(TableColumn.CellEditEvent<User, Role> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        if (user.getIdUser() == 0)
            commandsUser.put(user, "insert");
        else
            commandsUser.put(user, "update");
    }

    @FXML
    public void directionEditCommit(TableColumn.CellEditEvent<User, Direction> t) {
        int idRow = t.getTablePosition().getRow();
        User user = t.getTableView().getItems().get(idRow);
        user.setDirection(t.getNewValue());
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
        User userSelect = usersTable.getSelectionModel().getSelectedItem();
        commandsUser.put(userSelect, "delete");
        usersTable.getItems().removeAll(userSelect);
        users.removeAll(userSelect);
    }

    @FXML
    public void deleteUserKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            User userSelect = usersTable.getSelectionModel().getSelectedItem();
            commandsUser.put(userSelect, "delete");
            usersTable.getItems().removeAll(userSelect);
            users.removeAll(userSelect);
        }
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setRoles(ObservableList<Role> positions) {
        UsersPanelController.roles = positions;
    }

    public static void setProgressBar(ProgressBar progressBar) {
        UsersPanelController.progressBar = progressBar;
    }

    public static Label getTaskLabel() {
        return taskLabel;
    }

    private void initFio() {
        fioColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initLogin() {
        loginColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initRole() {
        positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, String> param) {
                User user = param.getValue();
                Role position = user.getRole();
                if (position != null) {
                    return position.roleNameProperty();
                }
                return null;
            }
        });
        positionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(roles));
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

    private void initDirection() {
        directions = restConnection.getRestDirection().getAll();
        directions.add(0, null);
        directionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Direction>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, Direction> param) {
                User user = param.getValue();
                Direction direction = user.getDirection();
                if (direction != null)
                    return direction.directionNameProperty();
                return null;
            }
        });
        directionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(directions));
    }

    private void initGroup() {
        groups = restConnection.getRestGroup().getAll();
        groups.add(0, null);
        groupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Group>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, Group> param) {
                User user = param.getValue();
                Group group = user.getGroup();
                if (group != null)
                    return group.groupNameProperty();
                return null;
            }
        });
        groupColumn.setCellFactory(ComboBoxTableCell.forTableColumn(groups));
    }

    private void initResetPassButton() {
        final ContextMenu cm = new ContextMenu();
        final MenuItem delMenuItem = new MenuItem("Удалить пользователя");
        cm.getItems().add(delMenuItem);
        final MenuItem resetMenuItem = new MenuItem("Сбросить");
        cm.getItems().add(resetMenuItem);

        usersTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                cm.show(usersTable, event.getScreenX(), event.getScreenY());
            } else {
                cm.hide();
            }
            delMenuItem.setOnAction((eventDel) -> {
                User usersSelect = usersTable.getSelectionModel().getSelectedItem();
                commandsUser.put(usersSelect, "delete");
                usersTable.getItems().removeAll(usersSelect);
                users.removeAll(usersSelect);
            });
            resetMenuItem.setOnAction((eventReset) -> {
                User user = usersTable.getSelectionModel().getSelectedItem();
                user.setStatus(0);
                user.setPassword(SecurityAES.encryptPass("12345"));
                commandsUser.put(user, "update");
            });
        });
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
            if (newValue != null && newValue.length() > 2) {
                User user = new User();
                user.setFio(newValue);
                user.setLogin(newValue);
                user.setEmail(newValue);
                user.setPhone(newValue);
                Role role = new Role();
                role.setRoleName(newValue);
                Group group = new Group();
                group.setGroupName(newValue);
                Direction direction = new Direction();
                direction.setDirectionName(newValue);
                user.setDirection(direction);
                user.setRole(role);
                user.setGroup(group);
                return restConnection.getRestUser().search(user);
            } else {
                return restConnection.getRestUser().getAll();
            }
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

    public static void setTaskLabel(Label taskLabel) {
        UsersPanelController.taskLabel = taskLabel;
    }

    @FXML
    public void saveUserAction(ActionEvent event) {
        SaveUserTask saveUserTask = new SaveUserTask();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(saveUserTask.progressProperty());
        Thread thread = new Thread(saveUserTask);
        thread.start();
    }

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        commandsUser = new HashMap<>();
        usersTable.setItems(users);
        initId();
        initFio();
        initLogin();
        initEmail();
        initPhone();
        initRole();
        initDirection();
        initGroup();
        initResetPassButton();
        initDate();
        initSearchUser();
    }

    private void initId() {
       /* idColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                return new ReadOnlyObjectWrapper(usersTable.getItems().indexOf(param.getValue()) + 1);
            }
        });*/
    }
}
