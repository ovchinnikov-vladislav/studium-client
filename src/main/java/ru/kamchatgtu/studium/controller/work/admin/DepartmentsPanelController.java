package ru.kamchatgtu.studium.controller.work.admin;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import ru.kamchatgtu.studium.engine.EditCell;
import ru.kamchatgtu.studium.entity.Group;

import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DepartmentsPanelController {

    private RestConnection rest;
    private ObservableList<Group> departments;
    private Map<Group, String> commandsDepartment;
    private static ProgressBar progressBar;
    private static Label taskLabel;

    @FXML
    private GridPane mainPanel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Group> departmentsTable;
    @FXML
    private TableColumn<Group, String> departmentNameColumn;
    @FXML
    private TableColumn<Group, String> departmentTeachersColumn;
    @FXML
    private Button addDepartmentButton,
                    deleteDepartmentButton,
                    saveDepartmentButton;

    @FXML
    public void initialize() {
        rest = new RestConnection();
        commandsDepartment = new HashMap<>();
        initDownloadDepartmentsAsync();
        mainPanel.visibleProperty().addListener(event -> {
            if (mainPanel.isVisible())
                initDownloadDepartmentsAsync();
        });
        initDepartmentNameColumn();
        initDepartmentTeachersColumn();
    }

    private void initDownloadDepartmentsAsync() {
        DownloadDepartmentsAsync async = new DownloadDepartmentsAsync();
        if (!async.isAlive())
            async.execute();
    }

    private void initDepartmentNameColumn() {
        departmentNameColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initDepartmentTeachersColumn() {
        departmentTeachersColumn.setCellValueFactory(param -> {
            String result = "";
            Set<User> userSet = param.getValue().getUsers();
            for (User user : userSet) {
                result += user.getFio() + "; ";
            }
            return new SimpleStringProperty(result);
        });
        departmentTeachersColumn.setCellFactory(param -> {
            TableCell<Group, String> cell = new TableCell<>();
            Text text = new Text();
            text.setStyle("-fx-fill: -fx-text-background-color;");
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
    }

    @FXML
    public void departmentNameEditCommit(TableColumn.CellEditEvent<Group, String> t) {
        int idRow = t.getTablePosition().getRow();
        Group group = t.getTableView().getItems().get(idRow);
        group.setGroupName(t.getNewValue());
        if (group.getIdGroup() == 0)
            commandsDepartment.put(group, "insert");
        else
            commandsDepartment.put(group, "update");
    }

    @FXML
    public void deleteDepartmentKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            Group groupSelect = departmentsTable.getSelectionModel().getSelectedItem();
            commandsDepartment.put(groupSelect, "delete");
            departmentsTable.getItems().removeAll(groupSelect);
            departments.removeAll(groupSelect);
        }
    }

    @FXML
    public void addDepartmentAction(ActionEvent event) {
        Group group = new Group();
        group.setRole(rest.getRestRole().get(2));
        if (departments != null) {
            if (departments.size() == 0 || departments.get(0).getGroupName() != null)
                departments.add(0, group);
        } else {
            departments = FXCollections.observableArrayList();
            departments.add(0, group);
        }
        departmentsTable.setItems(departments);
    }

    @FXML
    void deleteDepartmentAction(ActionEvent event) {
        Group groupSelect = departmentsTable.getSelectionModel().getSelectedItem();
        commandsDepartment.put(groupSelect, "delete");
        departmentsTable.getItems().removeAll(groupSelect);
        departments.removeAll(groupSelect);
    }

    @FXML
    void saveDepartmentAction(ActionEvent event) {
        Message message = new Message(Alert.AlertType.CONFIRMATION);
        message.setTitle("Сохранение кафедр");
        message.setContentText("Сохранение кафедр");
        message.setHeaderText("Вы уверены, что хотите сохранить кафедры?");
        message.initOwner(departmentsTable.getScene().getWindow());
        message.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> optional = message.showAndWait();
        if (optional.get() == ButtonType.OK) {
            SaveDepartmentAsync saveDepartmentAsync = new SaveDepartmentAsync();
            saveDepartmentAsync.execute();
        } else {
            DownloadDepartmentsAsync async = new DownloadDepartmentsAsync();
            async.execute();
        }

    }

    private class DownloadDepartmentsAsync extends AsyncTask<Void, Void, ObservableList<Group>> {
        @Override
        public void onPreExecute() {
            init(true);
            departmentsTable.setItems(null);
        }

        @Override
        public ObservableList<Group> doInBackground(Void... voids) {
            departments = rest.getRestGroup().getAdminAndTeacherGroups();
            for (Group group : departments)
                group.initUsers();
            return departments;
        }

        @Override
        public void onPostExecute(ObservableList<Group> groups) {
            departmentsTable.setItems(departments);
            init(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
//            searchTextField.setDisable(flag);
            departmentsTable.setDisable(flag);
            addDepartmentButton.setDisable(flag);
            deleteDepartmentButton.setDisable(flag);
            saveDepartmentButton.setDisable(flag);
        }
    }

    private class SaveDepartmentAsync extends AsyncTask<Void, Void, ObservableList<Group>> {

        private SaveDepartmentTask saveDepartmentTask;

        @Override
        public void onPreExecute() {
            saveDepartmentTask = new SaveDepartmentTask();
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(saveDepartmentTask.progressProperty());
            Thread thread = new Thread(saveDepartmentTask);
            thread.start();
        }

        @Override
        public ObservableList<Group> doInBackground(Void... voids) {
            try {
                return saveDepartmentTask.get();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return rest.getRestGroup().getAdminAndTeacherGroups();
        }

        @Override
        public void onPostExecute(ObservableList<Group> groups) {
            DepartmentsPanelController.this.departments = groups;
            departmentsTable.setItems(groups);
            Message message = new Message(Alert.AlertType.INFORMATION);
            message.setTitle("Сохранение кафедр");
            message.setHeaderText("Сохранение кафедр");
            message.setContentText("Кафедры были успешно сохранены.");
            message.initOwner(departmentsTable.getScene().getWindow());
            message.initModality(Modality.WINDOW_MODAL);
            message.showAndWait();
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class SaveDepartmentTask extends Task<ObservableList<Group>> {

        @Override
        protected ObservableList<Group> call() {
            int i = 0;
            int count = commandsDepartment.size();
            for (Map.Entry<Group, String> command : commandsDepartment.entrySet()) {
                Group group = command.getKey();
                String com = command.getValue();
                if ("update".equals(com))
                    rest.getRestGroup().update(group);
                else if ("insert".equals(com)) {
                    rest.getRestGroup().add(group);
                } else if ("delete".equals(com)) {
                    rest.getRestGroup().remove(group);
                }
                this.updateProgress(++i, count);
            }
            commandsDepartment = new HashMap<>();
            updateProgress(0, count);
            return rest.getRestGroup().getAdminAndTeacherGroups();
        }
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setProgressBar(ProgressBar progressBar) {
        DepartmentsPanelController.progressBar = progressBar;
    }

    public static void setTaskLabel(Label label) {
        DepartmentsPanelController.taskLabel = label;
    }
}
