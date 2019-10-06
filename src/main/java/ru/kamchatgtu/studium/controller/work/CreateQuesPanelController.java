package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.question.QuestionDialogController;
import ru.kamchatgtu.studium.controller.work.question.ThemeDialogController;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.engine.thread.ImportExcelAsync;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.Message;
import ru.kamchatgtu.studium.view.work.question.QuestionDialog;
import ru.kamchatgtu.studium.view.work.question.ThemeDialog;
import ru.kamchatgtu.studium.component.CustomTextArea;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CreateQuesPanelController {

    private static RestConnection rest;
    private Set<Answer> querySet;

    private static Theme selectedTheme;
    private static Question selectedQuestion;
    private static ObservableList<Theme> themes;
    private static ObservableList<Question> questions;

    private static ProgressBar progressBar;
    private static Label taskLabel;
    private ToggleGroup groupAnswers;

    @FXML
    private GridPane createQuesPanel;
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
    @FXML
    private Label labelAnswer;

    public static void setThemes(ObservableList<Theme> themes) {
        CreateQuesPanelController.themes = themes;
    }

    public static Theme getSelectedTheme() {
        return selectedTheme;
    }

    public static void setSelectedTheme(Theme theme) {
        themes = rest.getRestTheme().getAll();
        selectedTheme = theme;
    }

    public static Question getSelectedQuestion() {
        return selectedQuestion;
    }

    public static void setSelectedQuestion(Question question) {
        selectedQuestion = question;
    }

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setProgressBar(ProgressBar progressBar) {
        CreateQuesPanelController.progressBar = progressBar;
    }

    public static Label getTaskLabel() {
        return taskLabel;
    }

    @FXML
    public void initialize() {
        rest = new RestConnection();
        querySet = new HashSet<>();
        initThemesEvent();
        initQuestionsEvent();
        groupAnswers = new ToggleGroup();
        if (Security.USER_LOGIN.getRole().getAccess() == 2) {
            initPanel();
        }
    }

    private void initPanel() {
        createQuesPanel.visibleProperty().addListener(observable -> {
            themeBox.getSelectionModel().clearSelection();
            themeBox.setValue(null);
            initThemes();
            questionBox.getSelectionModel().clearSelection();
            questionBox.setValue(null);
            questionBox.setItems(null);
            answersPane.getChildren().removeAll(answersPane.getChildren());
            addAnswerButton.setVisible(false);
            labelAnswer.setVisible(true);
        });
    }

    private void initThemes() {
        DownloadThemesTask downloadThemesTask = new DownloadThemesTask();
        downloadThemesTask.execute();
    }

    private void initQuestions() {
        DownloadQuestionsTask downloadQuestionsTask = new DownloadQuestionsTask();
        downloadQuestionsTask.execute();
    }

    private void clearQuestions() {
        selectedQuestion = null;
        questions = null;
        questionBox.getSelectionModel().clearSelection();
        questionBox.setValue(null);
        questionBox.setItems(null);
        questionBox.setDisable(true);
        addQuestionButton.setDisable(true);
        saveQuestionButton.setDisable(true);
        editQuestionButton.setDisable(true);
        clearAnswers();
    }

    private void initAnswers() {
        clearAnswers();
        selectedQuestion.initAnswers();
        labelAnswer.setVisible(false);
        addAnswerButton.setVisible(false);
        if (selectedQuestion.getAnswers().size() == 0) {
            Answer answer = new Answer();
            answer.setUser(Security.USER_LOGIN);
            selectedQuestion.getAnswers().add(answer);
        }
        int type = selectedQuestion.getQuestionType();
        if (type == 1) {
            initOneAnswers();
        } else if (type == 2) {
            initMultiAnswers();
        } else if (type == 3) {
            initTextAnswers();
        }
    }

    private void clearAnswers() {
        answersPane.getChildren().removeAll(answersPane.getChildren());
        addAnswerButton.setVisible(false);
        labelAnswer.setVisible(true);
    }

    private void initOneAnswers() {
        addAnswerButton.setVisible(true);
        ObservableList<Answer> answers = selectedQuestion.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            initOneAnswer(i, answers.get(i));
        }
    }

    private void initOneAnswer(int row, Answer answer) {
        Label label = new Label();
        label.setText(row + 1 + ".");
        GridPane.setValignment(label, VPos.TOP);
        TextArea textArea = new CustomTextArea();
        textArea.setPromptText("Ответ");
        textArea.textProperty().bindBidirectional(answer.answerTextProperty());
        Button button = new Button();
        Image image = new Image(getClass().getResourceAsStream("/image/cross-circular-button-outline.png"));
        button.setGraphic(new ImageView(image));
        button.getStyleClass().add("buttonRound");
        button.setStyle("-fx-padding: 5px 5px 5px 5px");
        GridPane.setValignment(button, VPos.TOP);
        Tooltip tooltip = new Tooltip("Удалить ответ");
        tooltip.setStyle("-fx-font-size: 12px");
        button.setTooltip(tooltip);
        RadioButton radioButton = new RadioButton();
        radioButton.selectedProperty().bindBidirectional(answer.correctProperty());
        radioButton.setToggleGroup(groupAnswers);
        GridPane.setValignment(radioButton, VPos.TOP);
        answersPane.add(label, 0, row);
        answersPane.add(radioButton, 1, row);
        answersPane.add(textArea, 2, row);
        answersPane.add(button, 3, row);
        initChangeTextField(textArea);
        initChangeRadioButton(radioButton);
        initDeleteAnswerButton(answer, button, textArea, button, label, radioButton);
    }

    private void initMultiAnswers() {
        addAnswerButton.setVisible(true);
        ObservableList<Answer> answers = selectedQuestion.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            initMultiAnswer(i, answers.get(i));
        }
    }

    private void initMultiAnswer(int row, Answer answer) {
        Label label = new Label();
        label.setText(row + 1 + ".");
        GridPane.setValignment(label, VPos.TOP);
        TextArea textArea = new CustomTextArea();
        textArea.setPromptText("Ответ");
        textArea.textProperty().bindBidirectional(answer.answerTextProperty());
        Button button = new Button();
        Image image = new Image(getClass().getResourceAsStream("/image/cross-circular-button-outline.png"));
        button.setGraphic(new ImageView(image));
        button.getStyleClass().add("buttonRound");
        button.setStyle("-fx-padding: 5px 5px 5px 5px");
        GridPane.setValignment(button, VPos.TOP);
        Tooltip tooltip = new Tooltip("Удалить ответ");
        tooltip.setStyle("-fx-font-size: 12px");
        button.setTooltip(tooltip);
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().bindBidirectional(answer.correctProperty());
        GridPane.setValignment(checkBox, VPos.TOP);
        answersPane.add(label, 0, row);
        answersPane.add(checkBox, 1, row);
        answersPane.add(textArea, 2, row);
        answersPane.add(button, 3, row);
        initChangeTextField(textArea);
        initChangeCheckBox(checkBox);
        initDeleteAnswerButton(answer, button, textArea, button, label, checkBox);
    }

    private void initTextAnswers() {
        ObservableList<Answer> answers = selectedQuestion.getAnswers();
        TextArea textArea = new CustomTextArea();
        textArea.setPromptText("Ответ");
        textArea.textProperty().bindBidirectional(answers.get(0).answerTextProperty());
        answersPane.add(textArea, 2, 0);
        addAnswerButton.setVisible(false);
        initChangeTextField(textArea);
    }

    @FXML
    public void addThemeAction(ActionEvent event) {
        initThemeWindow(true, "Добавление темы");
        themeBox.setItems(themes);
        themeBox.getSelectionModel().clearSelection();
        clearQuestions();
        if (selectedTheme != null) {
            themeBox.getSelectionModel().select(selectedTheme);
            themeBox.setValue(selectedTheme);
        }
    }

    @FXML
    public void editThemeAction(ActionEvent event) {
        initThemeWindow(false, "Редактирование темы");
        themeBox.setItems(themes);
        if (selectedTheme != null) {
            themeBox.getSelectionModel().select(selectedTheme);
            themeBox.setValue(selectedTheme);
        } else {
            themeBox.getSelectionModel().clearSelection();
            themeBox.setValue(null);
            clearQuestions();
        }
    }

    private void initChangeTextField(TextArea textField) {
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

    private void initThemesEvent() {
        themeBox.getSelectionModel().selectedIndexProperty().addListener((observable -> {
            clearQuestions();
            if (themeBox.getSelectionModel().getSelectedIndex() != -1) {
                selectedTheme = themes.get(themeBox.getSelectionModel().getSelectedIndex());
                themeBox.requestFocus();
                themeBox.setValue(selectedTheme);
                themeBox.getSelectionModel().select(selectedTheme);
                editThemeButton.setDisable(false);
            }
            initQuestions();
        }));
    }

    @FXML
    public void addQuestionAction(ActionEvent event) {
        initQuestionWindow(true, "Добавление вопроса");
        questionBox.setValue(selectedQuestion);
        initAnswers();
        saveQuestionButton.setDisable(false);
    }

    @FXML
    public void editQuestionAction(ActionEvent event) {
        initQuestionWindow(false, "Редактирование вопроса");
        questionBox.getSelectionModel().clearSelection();
        if (selectedQuestion != null) {
            questionBox.getSelectionModel().select(selectedQuestion);
            initAnswers();
        } else {
            initQuestions();
        }
        questionBox.setValue(selectedQuestion);
        saveQuestionButton.setDisable(false);
    }

    private void initQuestionsEvent() {
        questionBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (questionBox.getSelectionModel().getSelectedIndex() != -1) {
                selectedQuestion = questions.get(questionBox.getSelectionModel().getSelectedIndex());
                editQuestionButton.setDisable(false);
                saveQuestionButton.setDisable(true);
                initAnswers();
            }
        });
    }

    @FXML
    public void saveQuestionAction(ActionEvent event) {
        Question question = null;
        if (selectedQuestion.getDateEdit() == null) {
            selectedQuestion.setDateEdit(new Date());
            question = rest.getRestQuestion().add(selectedQuestion);
        } else {
            question = rest.getRestQuestion().update(selectedQuestion);
        }
        if (question != null) {
            ObservableList<Answer> answers = selectedQuestion.getAnswers();
            for (Answer answer : answers) {
                answer.setQuestion(question);
                if (answer.getDateEdit() == null) {
                    answer.setDateEdit(new Date());
                    rest.getRestAnswer().add(answer);
                } else {
                    rest.getRestAnswer().update(answer);
                }
            }
            for (Answer answer : querySet)
                rest.getRestAnswer().remove(answer);
        }
        Message message = new Message(Alert.AlertType.INFORMATION);
        message.setTitle("Studium");
        message.setHeaderText("Сохранение вопроса");
        message.setContentText("Вопрос успешно сохранен.");
        message.showAndWait();
        questions = rest.getRestQuestion().getQuestionsByTheme(selectedTheme.getIdTheme());
        questionBox.setItems(questions);
        questionBox.setValue(selectedQuestion);
        questionBox.getSelectionModel().select(selectedQuestion);
        addQuestionButton.setDisable(false);
        editQuestionButton.setDisable(false);
        saveQuestionButton.setDisable(true);
    }

    @FXML
    public void addAnswerAction(ActionEvent event) {
        ObservableList<Answer> answers = selectedQuestion.getAnswers();
        if (answers != null && answers.size() > 0 &&
                answers.get(answers.size() - 1).getAnswerText() != null &&
                !answers.get(answers.size() - 1).equals("")) {
            int type = selectedQuestion.getQuestionType();
            Answer answer = new Answer();
            answer.setUser(Security.USER_LOGIN);
            if (type == 1) {
                initOneAnswer(answers.size(), answer);
                answers.add(answer);
            } else {
                initMultiAnswer(answers.size(), answer);
                answers.add(answer);
            }
        }
    }

    @FXML
    public void importExcelAction(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            ImportExcelAsync excelAsync = new ImportExcelAsync(taskLabel, progressBar, themes, themeBox, file);
            excelAsync.execute();
        }
    }

    private void initDeleteAnswerButton(Answer answer, Button button, Node... nodes) {
        ObservableList<Answer> answers = selectedQuestion.getAnswers();
        button.setOnAction((eventBut) -> {
            if (answers.size() > 1) {
                answersPane.getChildren().removeAll(nodes);
                querySet.add(answer);
                answers.remove(answer);
                saveQuestionButton.setDisable(false);
            }
        });
    }

    public static void setTaskLabel(Label taskLabel) {
        CreateQuesPanelController.taskLabel = taskLabel;
    }

    private void initThemeWindow(boolean isAdd, String title) {
        try {
            Stage primaryStage = (Stage) addThemeButton.getScene().getWindow();
            ThemeDialogController.setIsAdd(isAdd);
            Stage dialog = ThemeDialog.getStage();
            dialog.setTitle(title);
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void initQuestionWindow(boolean isAdd, String title) {
        try {
            Stage primaryStage = (Stage) addQuestionButton.getScene().getWindow();
            QuestionDialogController.setIsAdd(isAdd);
            Stage dialog = QuestionDialog.getStage();
            dialog.setTitle(title);
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private class DownloadThemesTask extends AsyncTask<Void, Void, ObservableList<Theme>> {
        @Override
        public void onPreExecute() {
        }

        @Override
        public ObservableList<Theme> doInBackground(Void... voids) {
            themes = rest.getRestTheme().getAll();
            return themes;
        }

        @Override
        public void onPostExecute(ObservableList<Theme> themes) {
            themeBox.setItems(themes);
            clearQuestions();
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }

    private class DownloadQuestionsTask extends AsyncTask<Void, Void, ObservableList<Question>> {


        @Override
        public void onPreExecute() {
            addQuestionButton.setDisable(false);
            editQuestionButton.setDisable(false);
            questionBox.setDisable(false);
        }

        @Override
        public ObservableList<Question> doInBackground(Void... voids) {
            if (selectedTheme != null) {
                questions = rest.getRestQuestion().getQuestionsByTheme(selectedTheme.getIdTheme());
            }
            return questions;
        }

        @Override
        public void onPostExecute(ObservableList<Question> questions) {
            questionBox.setItems(questions);
            clearAnswers();
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
