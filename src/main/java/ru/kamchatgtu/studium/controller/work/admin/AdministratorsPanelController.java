package ru.kamchatgtu.studium.controller.work.admin;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.engine.EditCell;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Role;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdministratorsPanelController {

    private RestConnection restConnection;
    private HashMap<User, String> commandsAdministrator;
    private static ObservableList<User> administrators;
    private static ProgressBar progressBar;
    private static Label taskLabel;

    @FXML
    private GridPane mainPanel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> administratorsTable;
    @FXML
    private TableColumn<User, String> fioAdministratorColumn;
    @FXML
    private TableColumn<User, String> loginAdministratorColumn;
    @FXML
    private TableColumn<User, String> phoneAdministratorColumn;
    @FXML
    private TableColumn<User, Date> dateRegAdministratorColumn;
    @FXML
    private TableColumn<User, String> emailAdministratorColumn;
    @FXML
    private Button addAdministratorButton, deleteAdministratorButton, saveAdministratorButton;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        initAdministratorsPanel();
    }

    private void initDownloadAdministratorAsync() {
        DownloadAdministratorsAsync administratorsAsync = new DownloadAdministratorsAsync();
        administratorsAsync.execute();
    }

    private void initAdministratorsPanel() {
        commandsAdministrator = new HashMap<>();
        initDownloadAdministratorAsync();
        mainPanel.visibleProperty().addListener(event -> {
           if (mainPanel.isVisible())
               initDownloadAdministratorAsync();
        });
        initFioAdministrator();
        initLoginAdministrator();
        initEmailAdministrator();
        initPhoneAdministrator();
        initResetPassButton();
        initDateAdministrator();
        initSearchAdministrator();
    }

    private void initFioAdministrator() {
        fioAdministratorColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initLoginAdministrator() {
        loginAdministratorColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initEmailAdministrator() {
        emailAdministratorColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initPhoneAdministrator() {
        phoneAdministratorColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initDateAdministrator() {
        dateRegAdministratorColumn.setCellFactory(column -> {
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

    private void initResetPassButton() {
        final ContextMenu cm = new ContextMenu();
        final MenuItem delMenuItem = new MenuItem("Удалить пользователя");
        cm.getItems().add(delMenuItem);
        final MenuItem resetMenuItem = new MenuItem("Сбросить");
        cm.getItems().add(resetMenuItem);

        administratorsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                cm.show(administratorsTable, event.getScreenX(), event.getScreenY());
            } else {
                cm.hide();
            }
            delMenuItem.setOnAction((eventDel) -> {
                User administratorsSelect = administratorsTable.getSelectionModel().getSelectedItem();
                commandsAdministrator.put(administratorsSelect, "delete");
                administratorsTable.getItems().removeAll(administratorsSelect);
                administrators.removeAll(administratorsSelect);
            });
            resetMenuItem.setOnAction((eventReset) -> {
                User student = administratorsTable.getSelectionModel().getSelectedItem();
                student.setStatus(0);
                student.setPassword(Security.encryptPass("12345"));
                commandsAdministrator.put(student, "update");
            });
        });
    }

    private void initSearchAdministrator() {
/*        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));*/
    }

    private class SaveAdministratorTask extends Task<ObservableList<User>> {

        @Override
        protected ObservableList<User> call() {
            ObservableList<User> administrators = FXCollections.observableArrayList();
            int i = 0;
            int count = commandsAdministrator.size();
            for (Map.Entry<User, String> command : commandsAdministrator.entrySet()) {
                User student = command.getKey();
                String com = command.getValue();
                if ("update".equals(com))
                    restConnection.getRestUser().update(student);
                else if ("insert".equals(com)) {
                    student.setPassword(Security.encryptPass(Security.getRandomPass()));
                    restConnection.getRestUser().add(student);
                } else if ("delete".equals(com)) {
                    restConnection.getRestUser().remove(student);
                }
                this.updateProgress(++i, count);
            }
            commandsAdministrator = new HashMap<>();
            return administrators;
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
                User student = new User();
                student.setFio(newValue);
                student.setLogin(newValue);
                student.setEmail(newValue);
                student.setPhone(newValue);
                Role role = new Role();
                role.setRoleName(newValue);
                Group group = new Group();
                group.setGroupName(newValue);
                Direction direction = new Direction();
                direction.setDirectionName(newValue);
                student.setDirection(direction);
                student.setRole(role);
                student.setGroup(group);
                return restConnection.getRestUser().search(student);
            } else {
                return restConnection.getRestUser().getAdministrators();
            }
        }

        @Override
        public void onPostExecute(ObservableList<User> aAdministrators) {
            administrators.setAll(aAdministrators);
            administratorsTable.setItems(administrators);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    @FXML
    public void fioAdministratorEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "fio");
    }

    @FXML
    public void loginAdministratorEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "login");
    }

    @FXML
    public void phoneAdministratorEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "phone");
    }

    @FXML
    public void emailAdministratorEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "email");
    }

    @FXML
    public void roleAdministratorEditCommit(TableColumn.CellEditEvent<User, Role> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void directionAdministratorEditCommit(TableColumn.CellEditEvent<User, Direction> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void groupAdministratorEditCommit(TableColumn.CellEditEvent<User, Group> t) {
        templateEditCommit(t, "");
    }

    private void templateEditCommit(TableColumn.CellEditEvent t, String flag) {
        int idRow = t.getTablePosition().getRow();
        Object object = t.getTableView().getItems().get(idRow);
        if (object instanceof User) {
            User student = (User) object;
            Object value = t.getNewValue();
            if (value instanceof Group)
                student.setGroup((Group) value);
            else if (value instanceof Direction)
                student.setDirection((Direction) value);
            else if (value instanceof Role)
                student.setRole((Role) value);
            else if (value instanceof String && flag.equals("email"))
                student.setEmail((String) value);
            else if (value instanceof String && flag.equals("phone"))
                student.setPhone((String) value);
            else if (value instanceof String && flag.equals("login"))
                student.setLogin((String) value);
            else if (value instanceof String && flag.equals("fio"))
                student.setFio((String) value);

            if (student.getIdUser() == 0)
                commandsAdministrator.put(student, "insert");
            else
                commandsAdministrator.put(student, "update");
        }
    }

    @FXML
    public void addAdministratorAction(ActionEvent event) {
        User newAdministrator = new User();
        newAdministrator.setDateAuth(new Date());
        newAdministrator.setDateReg(new Date());
        newAdministrator.setRole(restConnection.getRestRole().get(1));
        newAdministrator.setPassword(Security.encryptPass("12345"));
        if (administrators.get(0).getLogin() != null)
            administrators.add(0, newAdministrator);
        administratorsTable.setItems(administrators);
    }

    @FXML
    public void deleteAdministratorAction(ActionEvent event) {
        User studentSelect = administratorsTable.getSelectionModel().getSelectedItem();
        commandsAdministrator.put(studentSelect, "delete");
        administratorsTable.getItems().removeAll(studentSelect);
        administrators.removeAll(studentSelect);
    }

    @FXML
    public void deleteAdministratorKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            User studentSelect = administratorsTable.getSelectionModel().getSelectedItem();
            commandsAdministrator.put(studentSelect, "delete");
            administratorsTable.getItems().removeAll(studentSelect);
            administrators.removeAll(studentSelect);
        }
    }

    @FXML
    public void saveAdministratorAction(ActionEvent event) {
        SaveAdministratorTask saveAdministratorTask = new SaveAdministratorTask();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(saveAdministratorTask.progressProperty());
        Thread thread = new Thread(saveAdministratorTask);
        thread.start();
    }

    public static void setProgressBar(ProgressBar progressBar) {
        AdministratorsPanelController.progressBar = progressBar;
    }

    public static void setTaskLabel(Label taskLabel) {
        AdministratorsPanelController.taskLabel = taskLabel;
    }

    private class DownloadAdministratorsAsync extends AsyncTask<Void, Void, ObservableList<User>> {
        @Override
        public void onPreExecute() {
            init(true);
            administratorsTable.setItems(null);
        }

        @Override
        public ObservableList<User> doInBackground(Void... voids) {
            administrators = restConnection.getRestUser().getAdministrators();
            return administrators;
        }

        @Override
        public void onPostExecute(ObservableList<User> users) {
            administratorsTable.setItems(users);
            init(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
//            searchTextField.setDisable(flag);
            administratorsTable.setDisable(flag);
            addAdministratorButton.setDisable(flag);
            deleteAdministratorButton.setDisable(flag);
            saveAdministratorButton.setDisable(flag);
        }
    }
}
