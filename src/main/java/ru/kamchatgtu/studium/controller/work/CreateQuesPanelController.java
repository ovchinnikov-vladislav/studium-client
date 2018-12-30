package ru.kamchatgtu.studium.controller.work;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.question.QuestionDialogController;
import ru.kamchatgtu.studium.controller.work.question.ThemeDialogController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.message.Message;
import ru.kamchatgtu.studium.view.work.question.QuestionDialog;
import ru.kamchatgtu.studium.view.work.question.ThemeDialog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CreateQuesPanelController {

    private static RestConnection restConnection;
    private Set<Answer> querySet;

    private static Theme selectedTheme;
    private static Question selectedQuestion;
    private static ObservableList<Theme> themes;
    private static ObservableList<Question> questions;
    private ObservableList<Answer> answers;

    @FXML
    private ComboBox<Theme> themeBox;
    @FXML
    private Button addThemeButton;
    @FXML
    private Button editThemeButton;
    @FXML
    private ComboBox<Question> questionBox;
    @FXML
    private Button addQuestionButton;
    @FXML
    private Button editQuestionButton;
    @FXML
    private Button saveQuestionButton;
    @FXML
    private GridPane answersPane;
    @FXML
    private Button addAnswerButton;

    public static void setThemes(ObservableList<Theme> themes) {
        CreateQuesPanelController.themes = themes;
    }

    public static Theme getSelectedTheme() {
        return selectedTheme;
    }

    public static void setSelectedTheme(Theme theme) {
        themes = restConnection.getRestTheme().getAll();
        selectedTheme = theme;
    }

    public static Question getSelectedQuestion() {
        return selectedQuestion;
    }

    public static void setSelectedQuestion(Question question) {
        selectedQuestion = question;
    }

    @FXML
    public void initialize() {
        if (SecurityAES.USER_LOGIN.getPosition().getAccess() == 2) {
            selectedTheme = new Theme();
            selectedQuestion = new Question();
            querySet = new HashSet<>();
            restConnection = new RestConnection();
            initTheme();
            initQuestion();
        }
    }

    @FXML
    public void addNewThemeAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) addThemeButton.getScene().getWindow();
            ThemeDialogController.setIsAdd(true);
            Stage dialog = ThemeDialog.getStage();
            dialog.setTitle("Добавление темы");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            initTheme();
            themeBox.setValue(selectedTheme);
            if (selectedTheme != null)
                themeBox.getSelectionModel().select(selectedTheme);
            else
                themeBox.getSelectionModel().clearSelection();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void editThemeAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) editThemeButton.getScene().getWindow();
            ThemeDialogController.setIsAdd(false);
            Stage dialog = ThemeDialog.getStage();
            dialog.setTitle("Редактирование темы");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            initTheme();
            themeBox.setValue(selectedTheme);
            if (selectedTheme != null)
                themeBox.getSelectionModel().select(selectedTheme);
            else
                themeBox.getSelectionModel().clearSelection();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void addNewQuestionAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) addQuestionButton.getScene().getWindow();
            QuestionDialogController.setIsAdd(true);
            Stage dialog = QuestionDialog.getStage();
            dialog.setTitle("Добавление вопроса");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            initQuestion();
            initAnswers();
            questionBox.getSelectionModel().clearSelection();
            questionBox.setValue(selectedQuestion);
            if (selectedQuestion != null)
                questionBox.getSelectionModel().select(selectedQuestion);
            saveQuestionButton.setDisable(false);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void editQuestionAction(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) editQuestionButton.getScene().getWindow();
            QuestionDialogController.setIsAdd(false);
            Stage dialog = QuestionDialog.getStage();
            dialog.setTitle("Редактирование вопроса");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            initQuestion();
            questionBox.getSelectionModel().clearSelection();
            questionBox.setValue(selectedQuestion);
            saveQuestionButton.setDisable(false);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void saveQuestionAction(ActionEvent event) {
        Question question;
        if (selectedQuestion.getDateReg() == null) {
            selectedQuestion.setDateReg(new Date());
            question = restConnection.getRestQuestion().add(selectedQuestion);
        } else {
            question = restConnection.getRestQuestion().update(selectedQuestion);
        }
        if (question != null) {
            for (Answer answer : answers) {
                answer.setQuestion(question);
                if (answer.getDateEdit() == null) {
                    answer.setDateEdit(new Date());
                    restConnection.getRestAnswer().add(answer);
                } else {
                    restConnection.getRestAnswer().update(answer);
                }
            }
            for (Answer answer : querySet)
                restConnection.getRestAnswer().remove(answer);
        }
        questions = restConnection.getRestQuestion().getQuestionsByTheme(selectedTheme.getIdTheme());
        questionBox.setItems(questions);
        questionBox.setValue(selectedQuestion);
        Message message = new Message(Alert.AlertType.INFORMATION);
        message.setTitle("Studium");
        message.setHeaderText("Сохранение вопроса");
        message.setContentText("Вопрос успешно сохранен.");
        message.showAndWait();
        addQuestionButton.setDisable(false);
        editQuestionButton.setDisable(false);
        saveQuestionButton.setDisable(true);
    }

    @FXML
    public void addNewAnswerAction(ActionEvent event) {
        initAnswers();
    }

    private void initTheme() {
        themeBox.getItems().removeAll(themeBox.getItems());
        themeBox.getItems().addAll(themes);
        themeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            themeBox.getParent().requestFocus();
            if (newValue != null && !newValue.equals(oldValue)) {
                selectedTheme = newValue;
                questionBox.setValue(null);
                questionBox.getSelectionModel().clearSelection();
                answersPane.getChildren().removeAll(answersPane.getChildren());
                addAnswerButton.setVisible(false);
                editThemeButton.setDisable(false);
                addQuestionButton.setDisable(false);
                editQuestionButton.setDisable(false);
                questions = selectedTheme.getQuestions();
                questionBox.setDisable(false);
                questionBox.setItems(null);
                questionBox.setItems(questions);
                questionBox.show();
            }
        });
    }

    private void initQuestion() {
        questionBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            questionBox.getParent().requestFocus();
            if (newValue != null && !newValue.equals(oldValue)) {
                selectedQuestion = newValue;
                addAnswerButton.setVisible(false);
                editQuestionButton.setDisable(false);
                saveQuestionButton.setDisable(true);
                answers = selectedQuestion.getAnswers();
                initAnswers();
                editQuestionButton.setDisable(false);
                int type = selectedQuestion.getTypeQuestion();
                if (type == 1 || type == 2)
                    addAnswerButton.setVisible(true);
            }
        });
    }

    private void initAnswers() {

        answersPane.getChildren().removeAll(answersPane.getChildren());
        if (answers != null && answers.size() > 0) {
            String textAnswer = answers.get(answers.size() - 1).getTextAnswer();
            if (textAnswer != null && !textAnswer.equals("") && selectedQuestion.getTypeQuestion() != 3) {
                Answer answer = new Answer();
                answer.setUser(SecurityAES.USER_LOGIN);
                answers.add(answer);
            }
        } else {
            Answer answer = new Answer();
            answer.setUser(SecurityAES.USER_LOGIN);
            answers = FXCollections.observableArrayList();
            answers.add(answer);
        }
        if (selectedQuestion.getTypeQuestion() == 1) {
            initOneAnswers();
        } else if (selectedQuestion.getTypeQuestion() == 2) {
            initMultiAnswers();
        } else if (selectedQuestion.getTypeQuestion() == 3) {
            initTextAnswers();
        }
    }

    private void initOneAnswers() {
        int row = 0;
        ToggleGroup group = new ToggleGroup();
        for (Answer answer : answers) {
            Label label = new Label();
            label.setText(row + 1 + ".");
            TextField textField = new TextField();
            textField.setPromptText("Ответ");
            textField.textProperty().bindBidirectional(answer.textAnswerProperty());
            Button button = new Button();
            Image image = new Image(getClass().getResourceAsStream("/image/cross-circular-button-outline.png"));
            button.setGraphic(new ImageView(image));
            button.getStyleClass().add("buttonRound");
            button.setStyle("-fx-padding: 5px 5px 5px 5px");
            RadioButton radioButton = new RadioButton();
            radioButton.selectedProperty().bindBidirectional(answer.rightProperty());
            radioButton.setToggleGroup(group);
            answersPane.add(label, 0, row);
            answersPane.add(radioButton, 1, row);
            answersPane.add(textField, 2, row);
            answersPane.add(button, 3, row);
            row++;
            initChangeTextField(textField);
            initChangeRadioButton(radioButton);
            initDeleteAnswerButton(answer, button, textField, button, label, radioButton);
        }
    }

    private void initMultiAnswers() {
        int row = 0;
        for (Answer answer : answers) {
            Label label = new Label();
            label.setText(row + 1 + ".");
            TextField textField = new TextField();
            textField.setPromptText("Ответ");
            textField.textProperty().bindBidirectional(answer.textAnswerProperty());
            Button button = new Button();
            Image image = new Image(getClass().getResourceAsStream("/image/cross-circular-button-outline.png"));
            button.setGraphic(new ImageView(image));
            button.getStyleClass().add("buttonRound");
            button.setStyle("-fx-padding: 5px 5px 5px 5px");
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().bindBidirectional(answer.rightProperty());
            answersPane.add(label, 0, row);
            answersPane.add(checkBox, 1, row);
            answersPane.add(textField, 2, row);
            answersPane.add(button, 3, row);
            row++;
            initChangeTextField(textField);
            initChangeCheckBox(checkBox);
            initDeleteAnswerButton(answer, button, textField, button, label, checkBox);
        }
    }

    private void initTextAnswers() {
        for (Answer answer : answers) {
            TextField textField = new TextField();
            textField.setPromptText("Ответ");
            textField.textProperty().bindBidirectional(answer.textAnswerProperty());
            answersPane.add(textField, 2, 0);
            addAnswerButton.setVisible(false);
            initChangeTextField(textField);
        }
    }

    private void initDeleteAnswerButton(Answer answer, Button button, Node... nodes) {
        button.setOnAction((eventBut) -> {
            if (answers.size() > 1) {
                answersPane.getChildren().removeAll(nodes);
                querySet.add(answer);
                answers.remove(answer);
                initAnswers();
            }
        });
    }

    private void initChangeTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveQuestionButton.setDisable(false);
        });
    }

    private void initChangeRadioButton(RadioButton radioButton) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            saveQuestionButton.setDisable(false);
        });
    }

    private void initChangeCheckBox(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            saveQuestionButton.setDisable(false);
        });
    }
}
