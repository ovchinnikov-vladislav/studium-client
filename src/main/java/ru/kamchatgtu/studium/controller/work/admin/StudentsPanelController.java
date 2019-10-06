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

public class StudentsPanelController {

    private RestConnection restConnection;
    private HashMap<User, String> commandsStudent;
    private static ObservableList<User> students;
    private static ProgressBar progressBar;
    private static Label taskLabel;


    @FXML
    private GridPane mainPanel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> studentsTable;
    @FXML
    private TableColumn<User, String> fioStudentColumn;
    @FXML
    private TableColumn<User, String> loginStudentColumn;
    @FXML
    private TableColumn<User, String> phoneStudentColumn;
    @FXML
    private TableColumn<User, Date> dateRegStudentColumn;
    @FXML
    private TableColumn<User, String> emailStudentColumn;
    @FXML
    private TableColumn<User, Direction> directionStudentColumn;
    @FXML
    private TableColumn<User, Group> groupStudentColumn;
    @FXML
    private Button addStudentButton, deleteStudentButton, saveStudentButton;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        initStudentsPanel();
    }

    private void initDownloadStudentsAsync() {
        DownloadStudentsAsync downloadStudentsAsync = new DownloadStudentsAsync();
        downloadStudentsAsync.execute();
    }

    private void initStudentsPanel() {
        initDownloadStudentsAsync();
        mainPanel.visibleProperty().addListener(event-> {
            if (mainPanel.isVisible())
                initDownloadStudentsAsync();
        });
        commandsStudent = new HashMap<>();
        initFioStudent();
        initLoginStudent();
        initEmailStudent();
        initPhoneStudent();
        initDirectionStudent();
        initGroupStudent();
        initResetPassButton();
        initDateStudent();
        initSearchStudent();
    }

    private void initFioStudent() {
        fioStudentColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initLoginStudent() {
        loginStudentColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initEmailStudent() {
        emailStudentColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initPhoneStudent() {
        phoneStudentColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initDateStudent() {
        dateRegStudentColumn.setCellFactory(column -> {
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

    private void initDirectionStudent() {
        directionStudentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Direction>, ObservableValue<Direction>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<User, Direction> param) {
                User student = param.getValue();
                Direction direction = student.getDirection();
                if (direction != null)
                    return direction.directionNameProperty();
                return null;
            }
        });
    }

    private void initGroupStudent() {
        groupStudentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Group>, ObservableValue<Group>>() {
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

        studentsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                cm.show(studentsTable, event.getScreenX(), event.getScreenY());
            } else {
                cm.hide();
            }
            delMenuItem.setOnAction((eventDel) -> {
                User studentsSelect = studentsTable.getSelectionModel().getSelectedItem();
                commandsStudent.put(studentsSelect, "delete");
                studentsTable.getItems().removeAll(studentsSelect);
                students.removeAll(studentsSelect);
            });
            resetMenuItem.setOnAction((eventReset) -> {
                User student = studentsTable.getSelectionModel().getSelectedItem();
                student.setStatus(0);
                student.setPassword(Security.encryptPass("12345"));
                commandsStudent.put(student, "update");
            });
        });
    }

    private void initSearchStudent() {
/*        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            SearchTask searchTask = new SearchTask(newValue);
            searchTask.execute();
        }));*/
    }

    private class SaveStudentTask extends Task<ObservableList<User>> {

        @Override
        protected ObservableList<User> call() {
            ObservableList<User> students = FXCollections.observableArrayList();
            int i = 0;
            int count = commandsStudent.size();
            for (Map.Entry<User, String> command : commandsStudent.entrySet()) {
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
            commandsStudent = new HashMap<>();
            return students;
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
                return restConnection.getRestUser().getStudents();
            }
        }

        @Override
        public void onPostExecute(ObservableList<User> aStudents) {
            students.setAll(aStudents);
            studentsTable.setItems(students);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    @FXML
    public void fioStudentEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "fio");
    }

    @FXML
    public void loginStudentEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "login");
    }

    @FXML
    public void phoneStudentEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "phone");
    }

    @FXML
    public void emailStudentEditCommit(TableColumn.CellEditEvent<User, String> t) {
        templateEditCommit(t, "email");
    }

    @FXML
    public void roleStudentEditCommit(TableColumn.CellEditEvent<User, Role> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void directionStudentEditCommit(TableColumn.CellEditEvent<User, Direction> t) {
        templateEditCommit(t, "");
    }

    @FXML
    public void groupStudentEditCommit(TableColumn.CellEditEvent<User, Group> t) {
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
                commandsStudent.put(student, "insert");
            else
                commandsStudent.put(student, "update");
        }
    }

    @FXML
    public void addStudentAction(ActionEvent event) {
        User newStudent = new User();
        newStudent.setDateAuth(new Date());
        newStudent.setDateReg(new Date());
        newStudent.setRole(restConnection.getRestRole().get(3));
        newStudent.setPassword(Security.encryptPass("12345"));
        if (students.get(0).getLogin() != null && students.get(0).getGroup() != null)
            students.add(0, newStudent);
        studentsTable.setItems(students);
    }

    @FXML
    public void deleteStudentAction(ActionEvent event) {
        User studentSelect = studentsTable.getSelectionModel().getSelectedItem();
        commandsStudent.put(studentSelect, "delete");
        studentsTable.getItems().removeAll(studentSelect);
        students.removeAll(studentSelect);
    }

    @FXML
    public void deleteStudentKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            User studentSelect = studentsTable.getSelectionModel().getSelectedItem();
            commandsStudent.put(studentSelect, "delete");
            studentsTable.getItems().removeAll(studentSelect);
            students.removeAll(studentSelect);
        }
    }

    @FXML
    public void saveStudentAction(ActionEvent event) {
        SaveStudentTask saveStudentTask = new SaveStudentTask();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(saveStudentTask.progressProperty());
        Thread thread = new Thread(saveStudentTask);
        thread.start();
    }

    public static void setProgressBar(ProgressBar progressBar) {
        StudentsPanelController.progressBar = progressBar;
    }

    public static void setTaskLabel(Label taskLabel) {
        StudentsPanelController.taskLabel = taskLabel;
    }

    private class DownloadStudentsAsync extends AsyncTask<Void, Void, ObservableList<User>> {

        private ObservableList<Direction> directions;
        private ObservableList<Group> groups;

        @Override
        public void onPreExecute() {
            init(true);
            studentsTable.setItems(null);
        }

        @Override
        public ObservableList<User> doInBackground(Void... voids) {
            students = restConnection.getRestUser().getStudents();
            directions = restConnection.getRestDirection().getAll();
            groups = restConnection.getRestGroup().getStudentGroups();
            return students;
        }

        @Override
        public void onPostExecute(ObservableList<User> users) {
            directionStudentColumn.setCellFactory(ComboBoxTableCell.forTableColumn(directions));
            groupStudentColumn.setCellFactory(ComboBoxTableCell.forTableColumn(groups));
            studentsTable.setItems(users);
            init(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
//            searchTextField.setDisable(flag);
            studentsTable.setDisable(flag);
            addStudentButton.setDisable(flag);
            deleteStudentButton.setDisable(flag);
            saveStudentButton.setDisable(flag);
        }
    }
}
