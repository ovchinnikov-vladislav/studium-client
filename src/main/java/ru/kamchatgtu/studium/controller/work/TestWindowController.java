package ru.kamchatgtu.studium.controller.work;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class TestWindowController {

    private RestConnection rest;
    private ObservableList<Group> groups;
    private ObservableList<Question> questions;
    private ObservableList<Subject> subjects;

    @FXML
    private ComboBox<Subject> subjectsBox;

    @FXML
    private TableView<Group> groupsTable;
    @FXML
    private TableColumn idGroupColumn;
    @FXML
    private TableColumn positionColumn;
    @FXML
    private TableColumn checkSubjectColumn;

    @FXML
    private TableView<Question> questionsTable;
    @FXML
    private TableColumn idQuestionColumn;
    @FXML
    private TableColumn themeColumn;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn checkQuestionColumn;

    @FXML
    public void initialize() {
        rest = new RestConnection();
        subjects = rest.getRestSubject().getAll();
        subjectsBox.setItems(subjects);
        initSubjectBox();
        groups = rest.getRestGroup().getAll();
        groupsTable.setItems(groups);
        initIdGroupColumn();
        initPositionColumn();
        initCheckSubjectColumn();
        questions = rest.getRestQuestion().getAll();
        questionsTable.setItems(questions);
        initIdQuestionColumn();
        initThemeColumn();
        initTypeColumn();
        initCheckQuestionColumn();
    }

    private void initSubjectBox() {
        subjectsBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            for (int i = 0; i < groupsTable.getItems().size(); i++) {
                for (Group group : newValue.getGroups()) {
                    Group inTable = groupsTable.getItems().get(i);
                    if (inTable.equals(group))
                        inTable.setInSubject(true);
                }
            }
            groupsTable.setItems(groups);
        }));
    }

    private void initIdGroupColumn() {
        idGroupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Group, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Group, String> param) {
                return new ReadOnlyObjectWrapper(groupsTable.getItems().indexOf(param.getValue()) + 1);
            }
        });
    }

    private void initPositionColumn() {
        positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Group, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Group, String> param) {
                Group group = param.getValue();
                Position position = group.getPosition();
                if (position != null) {
                    return position.positionProperty();
                }
                return null;
            }
        });
    }

    private void initCheckSubjectColumn() {
        checkSubjectColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Group, Boolean>, ObservableValue<Boolean>>() {
                    //This callback tell the cell how to bind the data model 'Registered' property to
                    //the cell, itself.
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Group, Boolean> param) {
                        return param.getValue().inSubjectProperty();
                    }
                });
        checkSubjectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkSubjectColumn));
    }

    private void initIdQuestionColumn() {
        idQuestionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Question, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Question, String> param) {
                return new ReadOnlyObjectWrapper(questionsTable.getItems().indexOf(param.getValue()) + 1);
            }
        });
    }

    private void initThemeColumn() {
        themeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Question, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Question, String> param) {
                Question ques = param.getValue();
                Theme theme = ques.getTheme();
                if (theme != null) {
                    return theme.themeProperty();
                }
                return null;
            }
        });
    }

    private void initTypeColumn() {
        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Question, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Question, String> param) {
                Question ques = param.getValue();
                int type = ques.getTypeQuestion();
                if (type == 1)
                    return new SimpleStringProperty("С одним вариантом ответов");
                else if (type == 2)
                    return new SimpleStringProperty("С несколькими вариантами ответов");
                else if (type == 3)
                    return new SimpleStringProperty("Текстовый вариант ответа");
                return null;
            }
        });
    }

    private void initCheckQuestionColumn() {
        checkQuestionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Question, String>, TableCell<Question, String>> cellFactory =
                new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, String> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {

                            final CheckBox checkBox = new CheckBox();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    checkBox.setOnAction(event -> {

                                    });
                                    setGraphic(checkBox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        checkQuestionColumn.setCellFactory(cellFactory);
    }
}
