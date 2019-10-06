package ru.kamchatgtu.studium.controller.work.admin;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
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

public class TeachersPanelController {

    private RestConnection restConnection;
    private HashMap<User, String> commandsTeacher;
    private static ObservableList<User> teachers;
    private static ProgressBar progressBar;
    private static Label taskLabel;

    @FXML
    private GridPane mainPanel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> teachersTable;
    @FXML
    private TableColumn<User, String> fioTeacherColumn;
    @FXML
    private TableColumn<User, String> loginTeacherColumn;
    @FXML
    private TableColumn<User, String> phoneTeacherColumn;
    @FXML
    private TableColumn<User, Date> dateRegTeacherColumn;
    @FXML
    private TableColumn<User, String> emailTeacherColumn;
    @FXML
    private TableColumn<User, Group> groupTeacherColumn;
    @FXML
    private Button addTeacherButton, deleteTeacherButton, saveTeacherButton;


    @FXML
    public void initialize() {
        restConnection = new RestConnection();

        initTeachersPanel();
    }

    private void initDownloadTeachersAsync() {
        DownloadTeachersAsync downloadTeachersAsync = new DownloadTeachersAsync();
        downloadTeachersAsync.execute();
    }

    private void initTeachersPanel() {
        commandsTeacher = new HashMap<>();
        initDownloadTeachersAsync();
        mainPanel.visibleProperty().addListener(event -> {
            if (mainPanel.isVisible())
                initDownloadTeachersAsync();
        });
        initFioTeacher();
        initLoginTeacher();
        initEmailTeacher();
        initPhoneTeacher();
        initGroupTeacher();
        initResetPassButton();
        initDateTeacher();
        initSearchTeacher();
    }

    private void initFioTeacher() {
        fioTeacherColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initLoginTeacher() {
        loginTeacherColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initEmailTeacher() {
        emailTeacherColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initPhoneTeacher() {
        phoneTeacherColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initDateTeacher() {
        dateRegTeacherColumn.setCellFactory(column -> {
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

    private void initGroupTeacher() {
        groupTeacherColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Group>, ObservableValue<Group>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, Group> param) {
                User student = param.getValue();
                Group group = student.getGroup();
                if (group != null)
                    return group.groupNameProperty();
                return null;
            }
        });
    }

    private void initResetPassButton() {
        final ContextMenu cm = new ContextMenu();
        final MenuItem delMenuItem = new MenuItem("Удалить пользователя");
        cm.getItems().add(delMenuItem);
        final MenuItem resetMenuItem = new MenuItem("Сбросить");
        cm.getItems().add(resetMenuItem);

        teachersTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                cm.show(teachersTable, event.getScreenX(), event.getScreenY());
            } else {
                cm.hide();
            }
            delMenuItem.setOnAction((eventDel) -> {
                User teachersSelect = teachersTable.getSelectionModel().getSelectedItem();
                commandsTeacher.put(teachersSelect, "delete");
                teachersTable.getItems().removeAll(teachersSelect);
                teachers.removeAll(teachersSelect);
            });
            resetMenuItem.setOnAction((eventReset) -> {
                User student = teachersTable.getSelectionModel().getSelectedItem();
                student.setStatus(0);
                student.setPassword(Security.encryptPass("12345"));
                commandsTeacher.put(student, "update");
            });
        });
    }

    private void initSearchTeacher() {
/*        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));*/
    }

    private class SaveTeacherTask extends Task<ObservableList<User>> {

        @Override
        protected ObservableList<User> call() {
            ObservableList<User> teachers = FXCollections.observableArrayList();
            int i = 0;
            int count = commandsTeacher.size();
            for (Map.Entry<User, String> command : commandsTeacher.entrySet()) {
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
            commandsTeacher = new HashMap<>();
            return teachers;
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
                return restConnection.getRestUser().getTeachers();
            }
        }

        @Override
        public void onPostExecute(ObservableList<User> aTeachers) {
            teachers.setAll(aTeachers);
            teachersTable.setItems(teachers);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    @FXML
    public void fioTeacherEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "fio");
    }

    @FXML
    public void loginTeacherEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "login");
    }

    @FXML
    public void phoneTeacherEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "phone");
    }

    @FXML
    public void emailTeacherEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "email");
    }

    @FXML
    public void roleTeacherEditCommit(TableColumn.CellEditEvent<User, Role> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void directionTeacherEditCommit(TableColumn.CellEditEvent<User, Direction> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void groupTeacherEditCommit(TableColumn.CellEditEvent<User, Group> t) {
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
                commandsTeacher.put(student, "insert");
            else
                commandsTeacher.put(student, "update");
        }
    }

    @FXML
    public void addTeacherAction(ActionEvent event) {
        User newTeacher = new User();
        newTeacher.setDateAuth(new Date());
        newTeacher.setDateReg(new Date());
        newTeacher.setRole(restConnection.getRestRole().get(2));
        newTeacher.setPassword(Security.encryptPass("12345"));
        if (teachers.get(0).getLogin() != null && teachers.get(0).getGroup() != null)
            teachers.add(0, newTeacher);
        teachersTable.setItems(teachers);
    }

    @FXML
    public void deleteTeacherAction(ActionEvent event) {
        User studentSelect = teachersTable.getSelectionModel().getSelectedItem();
        commandsTeacher.put(studentSelect, "delete");
        teachersTable.getItems().removeAll(studentSelect);
        teachers.removeAll(studentSelect);
    }

    @FXML
    public void deleteTeacherKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            User studentSelect = teachersTable.getSelectionModel().getSelectedItem();
            commandsTeacher.put(studentSelect, "delete");
            teachersTable.getItems().removeAll(studentSelect);
            teachers.removeAll(studentSelect);
        }
    }

    @FXML
    public void saveTeacherAction(ActionEvent event) {
        SaveTeacherTask saveTeacherTask = new SaveTeacherTask();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(saveTeacherTask.progressProperty());
        Thread thread = new Thread(saveTeacherTask);
        thread.start();
    }

    public static void setProgressBar(ProgressBar progressBar) {
        TeachersPanelController.progressBar = progressBar;
    }

    public static void setTaskLabel(Label taskLabel) {
        TeachersPanelController.taskLabel = taskLabel;
    }

    private class DownloadTeachersAsync extends AsyncTask<Void, Void, ObservableList<User>> {

        private ObservableList<Group> teacherGroups;

        @Override
        public void onPreExecute() {
            init(true);
            teachersTable.setItems(null);
        }

        @Override
        public ObservableList<User> doInBackground(Void... voids) {
            teachers = restConnection.getRestUser().getTeachers();
            teacherGroups = restConnection.getRestGroup().getAdminAndTeacherGroups();
            return teachers;
        }

        @Override
        public void onPostExecute(ObservableList<User> users) {
            groupTeacherColumn.setCellFactory(ComboBoxTableCell.forTableColumn(teacherGroups));
            teachersTable.setItems(users);
            init(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
//            searchTextField.setDisable(flag);
            teachersTable.setDisable(flag);
            addTeacherButton.setDisable(flag);
            deleteTeacherButton.setDisable(flag);
            saveTeacherButton.setDisable(flag);
        }
    }
}
