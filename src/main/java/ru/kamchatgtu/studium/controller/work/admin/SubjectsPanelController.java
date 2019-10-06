package ru.kamchatgtu.studium.controller.work.admin;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.admin.subject.SubjectDialogController;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.work.admin.SubjectDialog;

public class SubjectsPanelController {

    private static RestConnection rest;
    private static ObservableList<Subject> subjects;
    private static Subject selectedSubject;
    private ObservableList<User> teachers;
    private ObservableList<Direction> directions;

    @FXML
    private GridPane mainPanel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ComboBox<Subject> subjectsBox;
    @FXML
    private Button addSubjectButton, editSubjectButton, saveAll1, saveAll2;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField searchDirectionsTextField;
    @FXML
    private TableView<Direction> directionsTable;
    @FXML
    private TableColumn<Direction, Boolean> checkDirectionColumn;

    @FXML
    private TextField searchTeachersTextField;
    @FXML
    private TableView<User> teachersTable;
    @FXML
    private TableColumn<User, Boolean> checkTeacherColumn;

    @FXML
    public void initialize() {
        rest = new RestConnection();
        initData();
        initCheckDirectionColumn();
        initCheckTeacherColumn();
    }

    private void initDownloadSubjectsDirectionsTeachersAsync() {
        DownloadSubjectsDirectionsTeachersAsync downloadSubjectsDirectionsTeachersAsync = new DownloadSubjectsDirectionsTeachersAsync();
        downloadSubjectsDirectionsTeachersAsync.execute();
    }

    private void initData() {
        initDownloadSubjectsDirectionsTeachersAsync();
        mainPanel.visibleProperty().addListener(event -> {
            if (mainPanel.isVisible())
                initDownloadSubjectsDirectionsTeachersAsync();
        });
        subjectsBox.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            teachersTable.setDisable(true);
            directionsTable.setDisable(true);
//            searchDirectionsTextField.setDisable(true);
      //      searchTeachersTextField.setDisable(true);
            saveAll1.setDisable(true);
            saveAll2.setDisable(true);
            for (int i = 0; i < directionsTable.getItems().size(); i++) {
                directionsTable.getItems().get(i).setInSubject(false);
            }
            for (int i = 0; i < teachersTable.getItems().size(); i++) {
                teachersTable.getItems().get(i).setInSubject(false);
            }
            if (subjectsBox.getSelectionModel().getSelectedIndex() != -1) {
                selectedSubject = subjects.get(subjectsBox.getSelectionModel().getSelectedIndex());
                editSubjectButton.setDisable(false);
                initDirectionsTable();
                initUsersTable();
                teachersTable.setDisable(false);
                directionsTable.setDisable(false);
//                searchTeachersTextField.setDisable(false);
//                searchDirectionsTextField.setDisable(false);
                saveAll1.setDisable(false);
                saveAll2.setDisable(false);
            }
        }));
    }

    private void initDirectionsTable() {
        for (int i = 0; i < directionsTable.getItems().size(); i++) {
            for (Direction direction : selectedSubject.getDirections()) {
                Direction inTable = directionsTable.getItems().get(i);
                if (inTable.equals(direction))
                    inTable.setInSubject(true);
            }
        }
    }

    private void initUsersTable() {
        for (int i = 0; i < teachersTable.getItems().size(); i++) {
            for (User user : selectedSubject.getUsers()) {
                User inTable = teachersTable.getItems().get(i);
                if (inTable.equals(user))
                    inTable.setInSubject(true);
            }
        }
    }

    private void initCheckDirectionColumn() {
        checkDirectionColumn.setCellValueFactory(param -> param.getValue().inSubjectProperty());
        checkDirectionColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkDirectionColumn));
    }

    private void initCheckTeacherColumn() {
        checkTeacherColumn.setCellValueFactory(param -> param.getValue().inSubjectProperty());
        checkTeacherColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkTeacherColumn));
    }

    @FXML
    public void addSubjectAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) addSubjectButton.getScene().getWindow();
            SubjectDialogController.setIsAdd(true);
            Stage dialog = SubjectDialog.getStage();
            dialog.setTitle("Добавление дисциплины");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            subjectsBox.setItems(null);
            subjects = rest.getRestSubject().getAll();
            subjectsBox.setItems(subjects);
            subjectsBox.getSelectionModel().select(selectedSubject);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void editSubjectAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) editSubjectButton.getScene().getWindow();
            SubjectDialogController.setIsAdd(false);
            Stage dialog = SubjectDialog.getStage();
            dialog.setTitle("Редактирование дисциплины");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            subjectsBox.setItems(null);
            subjects = rest.getRestSubject().getAll();
            subjectsBox.setItems(subjects);
            subjectsBox.getSelectionModel().select(selectedSubject);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void saveAll(ActionEvent event) {
        SaveSubjectAsync saveSubjectAsync = new SaveSubjectAsync();
        saveSubjectAsync.execute();
    }

    public static Subject getSelectedSubject() {
        return selectedSubject;
    }

    public static void setSelectedSubject(Subject subject) {
        subjects = rest.getRestSubject().getAll();
        selectedSubject = subject;
    }

    private class SaveSubjectAsync extends AsyncTask<Void, Void, Boolean> {
        @Override
        public void onPreExecute() {

        }

        @Override
        public Boolean doInBackground(Void... voids) {
            if (selectedSubject != null) {
                selectedSubject.getDirections().removeAll(selectedSubject.getDirections());
                selectedSubject.getUsers().removeAll(selectedSubject.getUsers());
            }
            for (int i = 0; i < directionsTable.getItems().size(); i++) {
                Direction direction = directionsTable.getItems().get(i);
                if (direction.isInSubject())
                    selectedSubject.getDirections().add(direction);
            }
            for (int i = 0; i < teachersTable.getItems().size(); i++) {
                User user = teachersTable.getItems().get(i);
                if (user.isInSubject())
                    selectedSubject.getUsers().add(user);
            }
            selectedSubject = rest.getRestSubject().update(selectedSubject);
            subjects = rest.getRestSubject().getAll();
            if (selectedSubject != null)
                return true;
            return false;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            subjectsBox.setItems(subjects);
            subjectsBox.getSelectionModel().select(selectedSubject);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class DownloadSubjectsDirectionsTeachersAsync extends AsyncTask<Void, Void, Boolean> {
        @Override
        public void onPreExecute() {
            init(true);
            subjectsBox.setItems(null);
            directionsTable.setItems(null);
            teachersTable.setItems(null);
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            subjects = rest.getRestSubject().getAll();
            directions = rest.getRestDirection().getAll();
            teachers = rest.getRestUser().getTeachers();
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            progressIndicator.setVisible(false);
            subjectsBox.setDisable(false);
            addSubjectButton.setDisable(false);
            tabPane.setDisable(false);
            subjectsBox.setItems(subjects);
            directionsTable.setItems(directions);
            teachersTable.setItems(teachers);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void init(boolean flag) {
            progressIndicator.setVisible(flag);
            subjectsBox.setDisable(flag);
            addSubjectButton.setDisable(flag);
            editSubjectButton.setDisable(flag);
            tabPane.setDisable(flag);
//            searchDirectionsTextField.setDisable(flag);
            directionsTable.setDisable(flag);
            saveAll1.setDisable(flag);
//            searchTeachersTextField.setDisable(flag);
            teachersTable.setDisable(flag);
            saveAll2.setDisable(flag);
        }
    }
}
