package ru.kamchatgtu.studium.controller.work.question;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.work.CreateQuesPanelController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.restclient.RestConnection;

public class QuestionDialogController {

    private static boolean isAdd;
    private RestConnection rest;
    private Question question;
    @FXML
    private TextField questionField;
    @FXML
    private ToggleGroup answersToggle;
    @FXML
    private RadioButton oneAnswer;
    @FXML
    private RadioButton multiAnswer;
    @FXML
    private RadioButton textAnswer;
    @FXML
    private Button deleteButton;
    @FXML
    private HBox buttons;

    public static void setIsAdd(boolean isAdd) {
        QuestionDialogController.isAdd = isAdd;
    }

    @FXML
    public void initialize() {
        questionField.setText("");
        rest = new RestConnection();
        if (isAdd) {
            buttons.getChildren().remove(deleteButton);
            question = new Question();
        } else {
            Question q = CreateQuesPanelController.getSelectedQuestion();
            question = (Question) q.clone();
        }
        questionField.textProperty().bindBidirectional(question.textQuestionProperty());
        if (question.getTypeQuestion() == 0)
            question.setTypeQuestion(1);
        int type = question.getTypeQuestion();
        if (type == 1)
            oneAnswer.setSelected(true);
        else if (type == 2)
            multiAnswer.setSelected(true);
        else if (type == 3)
            textAnswer.setSelected(true);
        initToggle();
    }

    @FXML
    public void saveAction(ActionEvent event) {
        question.setTheme(CreateQuesPanelController.getSelectedTheme());
        question.setUser(SecurityAES.USER_LOGIN);
        CreateQuesPanelController.setSelectedQuestion(question);
        close((Node) event.getSource());
    }

    @FXML
    public void deleteAction(ActionEvent event) {
        RestConnection rest = new RestConnection();
        question.initAnswers();
        for (Answer answer : question.getAnswers())
            rest.getRestAnswer().remove(answer);
        rest.getRestQuestion().remove(question);
        CreateQuesPanelController.setSelectedQuestion(null);
        close((Node) event.getSource());
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        close((Node) event.getSource());
    }

    private void close(Node node) {
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    private void initToggle() {
        answersToggle.selectedToggleProperty().addListener((observalbe, old_tog, new_tog) -> {
            if (answersToggle.getSelectedToggle() != null) {
                if (oneAnswer.isSelected())
                    question.setTypeQuestion(1);
                else if (multiAnswer.isSelected())
                    question.setTypeQuestion(2);
                else if (textAnswer.isSelected())
                    question.setTypeQuestion(3);
            }
        });
    }
}
