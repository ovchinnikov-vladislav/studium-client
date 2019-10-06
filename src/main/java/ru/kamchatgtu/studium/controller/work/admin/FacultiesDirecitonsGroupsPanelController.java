package ru.kamchatgtu.studium.controller.work.admin;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.admin.direction.DirectionDialogController;
import ru.kamchatgtu.studium.controller.work.admin.faculty.FacultyDialogController;
import ru.kamchatgtu.studium.engine.EditCell;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.entity.Faculty;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.Message;
import ru.kamchatgtu.studium.view.work.admin.DirectionDialog;
import ru.kamchatgtu.studium.view.work.admin.FacultyDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FacultiesDirecitonsGroupsPanelController {

    private RestConnection restConnection;

    private static Faculty selectedFaculty;
    private ObservableList<Faculty> faculties;
    private static Direction selectedDirection;
    private ObservableList<Direction> directions;
    private ObservableList<Group> groups;
    private Map<Group, String> commandsGroup;
    private static ProgressBar progressBar;
    private static Label taskLabel;


    @FXML
    private GridPane mainPanel;
    @FXML
    private ComboBox<Faculty> facultiesBox;
    @FXML
    private ComboBox<Direction> directionsBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Group> groupsTable;
    @FXML
    private TableColumn<Group, String> groupNameColumn;
    @FXML
    private TableColumn<Group, String> groupStudentsColumn;
    @FXML
    private Button addFacultyButton, editFacultyButton,
            addDirectionButton, editDirectionButton,
            addGroupButton, deleteGroupButton, saveGroupButton;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        restConnection = new RestConnection();
        commandsGroup = new HashMap<>();
        initDownloadFacultiesAsync();
        mainPanel.visibleProperty().addListener(event -> {
            if (mainPanel.isVisible())
                initDownloadFacultiesAsync();
        });
        groupsTable.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            groupsTable.refresh();

            // close text box
            groupsTable.edit(-1, null);
        });
    }

    private void initDownloadFacultiesAsync() {
        DownloadFacultiesAsync downloadFacultiesAsync = new DownloadFacultiesAsync();
        downloadFacultiesAsync.execute();
    }

    private void initFaculties() {
        facultiesBox.setItems(faculties);
        facultiesBox.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            clearDirections();
            if (facultiesBox.getSelectionModel().getSelectedIndex() != -1) {
                selectedFaculty = faculties.get(facultiesBox.getSelectionModel().getSelectedIndex());
                facultiesBox.requestFocus();
                facultiesBox.setValue(selectedFaculty);
                facultiesBox.getSelectionModel().select(selectedFaculty);
                editFacultyButton.setDisable(false);
                DownloadDirectionsAsync async = new DownloadDirectionsAsync();
                if (!async.isAlive())
                    async.execute();
            }
        });
    }

    private void clearDirections() {
        selectedDirection = null;
        directions = null;
        directionsBox.getSelectionModel().clearSelection();
        directionsBox.setValue(null);
        directionsBox.setItems(null);
        directionsBox.setDisable(true);
        addDirectionButton.setDisable(true);
        editDirectionButton.setDisable(true);
    }

    private void initDirections() {
        if (selectedFaculty != null) {
            addDirectionButton.setDisable(false);
            directionsBox.setDisable(false);
            directionsBox.setItems(directions);
            directionsBox.getSelectionModel().selectedIndexProperty().addListener(observable -> {
                clearGroups();
                if (directionsBox.getSelectionModel().getSelectedIndex() != -1) {
                    selectedDirection = directions.get(directionsBox.getSelectionModel().getSelectedIndex());
                    directionsBox.requestFocus();
                    directionsBox.setValue(selectedDirection);
                    directionsBox.getSelectionModel().select(selectedDirection);
                    editDirectionButton.setDisable(false);
                    DownloadGroupsAsync async = new DownloadGroupsAsync();
                    async.execute();
                }
            });
        }
    }

    private void clearGroups() {
        groups = null;
        groupsTable.setItems(null);
        groupsTable.setDisable(true);
//        searchTextField.setDisable(true);
        addGroupButton.setDisable(true);
        deleteGroupButton.setDisable(true);
        saveGroupButton.setDisable(true);
    }

    private void initGroups() {
        if (selectedDirection != null) {
//            searchTextField.setDisable(false);
            addGroupButton.setDisable(false);
            deleteGroupButton.setDisable(false);
            saveGroupButton.setDisable(false);
            groupsTable.setDisable(false);
            groupsTable.setItems(groups);
            initGroupName();
            initGroupStudentsColumn();
        }
    }

    private void initGroupName() {
        groupNameColumn.setCellFactory(column -> EditCell.createStringEditCell());
    }

    private void initGroupStudentsColumn() {
        groupStudentsColumn.setCellValueFactory(param -> {
            String result = "";
            Set<User> userSet = param.getValue().getUsers();
            for (User user : userSet) {
                result += user.getFio() + "; ";
            }
            return new SimpleStringProperty(result);
        });
        groupStudentsColumn.setCellFactory(param -> {
            TableCell<Group, String> cell = new TableCell<>();
            Text text = new Text();
            text.setStyle("-fx-fill: -fx-text-background-color;");
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    @FXML
    public void addFacultyAction(ActionEvent event) {
        initFacultyWindow(true, "Добавление факультета");
        facultiesBox.getSelectionModel().clearSelection();
        clearDirections();
        if (selectedFaculty != null) {
            facultiesBox.getSelectionModel().select(selectedFaculty);
            facultiesBox.setValue(selectedFaculty);
        }
    }

    @FXML
    public void editFacultyAction(ActionEvent event) {
        initFacultyWindow(false, "Редактирование факультета");
        facultiesBox.getSelectionModel().clearSelection();
        if (selectedFaculty != null) {
            facultiesBox.getSelectionModel().select(selectedFaculty);
            facultiesBox.setValue(selectedFaculty);
        } else {
            facultiesBox.getSelectionModel().clearSelection();
            facultiesBox.setValue(null);
            clearDirections();
        }
    }

    private void initFacultyWindow(boolean isAdd, String title) {
        try {
            Stage primaryStage = (Stage) addFacultyButton.getScene().getWindow();
            FacultyDialogController.setIsAdd(isAdd);
            Stage dialog = FacultyDialog.getStage();
            dialog.setTitle(title);
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            faculties = null;
            facultiesBox.setItems(null);
            faculties = restConnection.getRestFaculty().getAll();
            facultiesBox.setItems(faculties);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void addDirectionAction(ActionEvent event) {
        initDirectionWindow(true, "Добавление направления");
        directionsBox.setValue(selectedDirection);
        initGroups();
    }

    @FXML
    public void editDirectionAction(ActionEvent event) {
        initDirectionWindow(false, "Редактирование направление");
        directionsBox.getSelectionModel().clearSelection();
        if (selectedDirection != null) {
            directionsBox.getSelectionModel().select(selectedDirection);
            initGroups();
        } else {
            initDirections();
        }
        directionsBox.setValue(selectedDirection);
    }

    private void initDirectionWindow(boolean isAdd, String title) {
        try {
            Stage primaryStage = (Stage) addDirectionButton.getScene().getWindow();
            DirectionDialogController.setIsAdd(isAdd);
            Stage dialog = DirectionDialog.getStage();
            dialog.setTitle(title);
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            directions = null;
            directionsBox.setItems(null);
            directions = restConnection.getRestDirection().getByFaculty(selectedFaculty.getIdFaculty());
            directionsBox.setItems(directions);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void deleteGroupKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            Group groupSelect = groupsTable.getSelectionModel().getSelectedItem();
            commandsGroup.put(groupSelect, "delete");
            groupsTable.getItems().removeAll(groupSelect);
            groups.removeAll(groupSelect);
        }
    }

    @FXML
    public void addGroupAction(ActionEvent event) {
        Group group = new Group();
        group.setDirection(selectedDirection);
        group.setRole(restConnection.getRestRole().get(3));
        if (groups.get(0).getGroupName() != null)
            groups.add(0, group);
        groupsTable.setItems(groups);
    }

    @FXML
    public void deleteGroupAction(ActionEvent event) {
        Group groupSelect = groupsTable.getSelectionModel().getSelectedItem();
        commandsGroup.put(groupSelect, "delete");
        groupsTable.getItems().removeAll(groupSelect);
        groups.removeAll(groupSelect);
    }

    @FXML
    public void saveGroupAction(ActionEvent event) {
        Message message = new Message(Alert.AlertType.CONFIRMATION);
        message.setTitle("Сохранение групп");
        message.setContentText("Сохранение групп");
        message.setHeaderText("Вы уверены, что хотите сохранить группы?");
        message.initOwner(groupsTable.getScene().getWindow());
        message.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> optional = message.showAndWait();
        if (optional.get() == ButtonType.OK) {
            SaveGroupAsync saveGroupAsync = new SaveGroupAsync();
            saveGroupAsync.execute();
        } else {
            DownloadGroupsAsync async = new DownloadGroupsAsync();
            async.execute();
        }
    }

    @FXML
    public void groupNameEditCommit(TableColumn.CellEditEvent<Group, String> t) {
        int idRow = t.getTablePosition().getRow();
        Group group = t.getTableView().getItems().get(idRow);
        group.setGroupName(t.getNewValue());
        if (group.getIdGroup() == 0)
            commandsGroup.put(group, "insert");
        else
            commandsGroup.put(group, "update");
    }

    public static Faculty getSelectedFaculty() {
        return FacultiesDirecitonsGroupsPanelController.selectedFaculty;
    }

    public static void setSelectedFaculty(Faculty faculty) {
        FacultiesDirecitonsGroupsPanelController.selectedFaculty = faculty;
    }

    public static Direction getSelectedDirection() {
        return FacultiesDirecitonsGroupsPanelController.selectedDirection;
    }

    public static void setSelectedDirection(Direction direction) {
        FacultiesDirecitonsGroupsPanelController.selectedDirection = direction;
    }

    private class DownloadFacultiesAsync extends AsyncTask<Void, Void, ObservableList<Faculty>> {
        @Override
        public void onPreExecute() {
            init(true);
        }

        @Override
        public ObservableList<Faculty> doInBackground(Void... voids) {
            faculties = restConnection.getRestFaculty().getAll();
            return faculties;
        }

        @Override
        public void onPostExecute(ObservableList<Faculty> faculties) {
            initFaculties();
            progressIndicator.setVisible(false);
            facultiesBox.setDisable(false);
            addFacultyButton.setDisable(false);
        }

        @Override
        public void progressCallback(Void... voids) {
        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
            facultiesBox.setDisable(flag);
            addFacultyButton.setDisable(flag);
            editFacultyButton.setDisable(flag);
            directionsBox.setDisable(flag);
            addDirectionButton.setDisable(flag);
            editDirectionButton.setDisable(flag);
//            searchTextField.setDisable(flag);
            groupsTable.setDisable(flag);
            addGroupButton.setDisable(flag);
            deleteGroupButton.setDisable(flag);
            saveGroupButton.setDisable(flag);
        }
    }

    private class DownloadDirectionsAsync extends AsyncTask<Void, Void, ObservableList<Direction>> {
        @Override
        public void onPreExecute() {
            init(true);
        }

        @Override
        public ObservableList<Direction> doInBackground(Void... voids) {
            directions = restConnection.getRestDirection().getByFaculty(selectedFaculty.getIdFaculty());
            return directions;
        }

        @Override
        public void onPostExecute(ObservableList<Direction> directions) {
            initDirections();
            progressIndicator.setVisible(false);
            directionsBox.setDisable(false);
            addDirectionButton.setDisable(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
            directionsBox.setDisable(flag);
            addDirectionButton.setDisable(flag);
            editDirectionButton.setDisable(flag);
//            searchTextField.setDisable(flag);
            groupsTable.setDisable(flag);
            addGroupButton.setDisable(flag);
            deleteGroupButton.setDisable(flag);
            saveGroupButton.setDisable(flag);
        }
    }

    private class DownloadGroupsAsync extends AsyncTask<Void, Void, ObservableList<Group>> {
        @Override
        public void onPreExecute() {
            init(true);
        }

        @Override
        public ObservableList<Group> doInBackground(Void... voids) {
            groups = restConnection.getRestGroup().getGroupsByDirection(selectedDirection.getIdDirection());
            if (groups != null) {
                for (Group group : groups)
                    group.initUsers();
            }
            return groups;
        }

        @Override
        public void onPostExecute(ObservableList<Group> groups) {
            initGroups();
            init(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
//            searchTextField.setDisable(flag);
            groupsTable.setDisable(flag);
            addGroupButton.setDisable(flag);
            deleteGroupButton.setDisable(flag);
            saveGroupButton.setDisable(flag);
        }
    }

    private class SaveGroupAsync extends AsyncTask<Void, Void, ObservableList<Group>> {

        private SaveGroupTask saveGroupTask;

        @Override
        public void onPreExecute() {
            saveGroupTask = new SaveGroupTask();
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(saveGroupTask.progressProperty());
            Thread thread = new Thread(saveGroupTask);
            thread.start();
        }

        @Override
        public ObservableList<Group> doInBackground(Void... voids) {
            try {
                return saveGroupTask.get();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return restConnection.getRestGroup().getGroupsByDirection(selectedDirection.getIdDirection());
        }

        @Override
        public void onPostExecute(ObservableList<Group> groups) {
            FacultiesDirecitonsGroupsPanelController.this.groups = groups;
            groupsTable.setItems(groups);
            Message message = new Message(Alert.AlertType.INFORMATION);
            message.setTitle("Сохранение групп");
            message.setHeaderText("Сохранение групп");
            message.setContentText("Группы были успешно сохранены.");
            message.initOwner(groupsTable.getScene().getWindow());
            message.initModality(Modality.WINDOW_MODAL);
            message.showAndWait();
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class SaveGroupTask extends Task<ObservableList<Group>> {

        @Override
        protected ObservableList<Group> call() {
            int i = 0;
            int count = commandsGroup.size();
            for (Map.Entry<Group, String> command : commandsGroup.entrySet()) {
                Group group = command.getKey();
                String com = command.getValue();
                if ("update".equals(com))
                    restConnection.getRestGroup().update(group);
                else if ("insert".equals(com)) {
                    restConnection.getRestGroup().add(group);
                } else if ("delete".equals(com)) {
                    restConnection.getRestGroup().remove(group);
                }
                this.updateProgress(++i, count);
            }
            commandsGroup = new HashMap<>();
            updateProgress(0, count);
            return restConnection.getRestGroup().getGroupsByDirection(selectedDirection.getIdDirection());
        }
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setProgressBar(ProgressBar progressBar) {
        FacultiesDirecitonsGroupsPanelController.progressBar = progressBar;
    }

    public static void setTaskLabel(Label label) {
        FacultiesDirecitonsGroupsPanelController.taskLabel = label;
    }
}
