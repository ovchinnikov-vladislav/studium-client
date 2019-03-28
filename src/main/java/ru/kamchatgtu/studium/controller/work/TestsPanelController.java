package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.controller.TestWindowController;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.test.TestWindow;

public class TestsPanelController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private GridPane testsPanel;

    @FXML
    private GridPane testsPane;

    @FXML
    public void initialize() {
        testsPanel.visibleProperty().addListener(observable -> {
            DownloadPane downloadPane = new DownloadPane();
            downloadPane.execute();
        });
    }

    private void openTestInit(Hyperlink hyperlink, Test test) {
        hyperlink.setOnAction(event -> {
            try {
                TestWindowController.setSelectedTest(test);
                Stage stage = TestWindow.getStage();
                stage.setTitle(test.getTestName());
                stage.initOwner(hyperlink.getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });
    }

    private class DownloadPane extends AsyncTask<Void, Void, ObservableList<Subject>> {

        private RestConnection restConnection = new RestConnection();

        @Override
        public void onPreExecute() {
            scrollPane.setVisible(false);
            progressIndicator.setVisible(true);
        }

        @Override
        public ObservableList<Subject> doInBackground(Void... voids) {
            ObservableList<Subject> subjects = FXCollections.observableArrayList();
            int access = SecurityAES.USER_LOGIN.getRole().getAccess();
            if (access == 3)
                subjects = restConnection.getRestSubject().getByDirection(SecurityAES.USER_LOGIN.getDirection());
            else if (access == 2)
                subjects = restConnection.getRestSubject().getByUser(SecurityAES.USER_LOGIN.getIdUser());
            for (Subject subject : subjects) {
                subject.setTests(restConnection.getRestTest().getTestsBySubject(subject.getIdSubject()));
            }
            return subjects;
        }

        @Override
        public void onPostExecute(ObservableList<Subject> subjects) {
            initTests(subjects);
            scrollPane.setVisible(true);
            progressIndicator.setVisible(false);
        }

        @Override
        public void progressCallback(Void... voids) {

        }

        private void initTests(ObservableList<Subject> subjects) {
            testsPane.getChildren().removeAll(testsPane.getChildren());

            int rowSubject = 0;
            for (Subject subject : subjects) {
                ObservableList<Test> tests = subject.getTests();
                if (tests.size() == 0) continue;
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(5));
                gridPane.getStyleClass().add("borderTest");

                ColumnConstraints column = new ColumnConstraints();
                column.setHgrow(Priority.ALWAYS);

                gridPane.getColumnConstraints().add(column);

                Label label = new Label((rowSubject + 1) + ". " + subject.getSubjectName());
                label.setStyle("-fx-text-fill: #728cb7; -fx-font-size: 16; -fx-font-weight: bold");
                GridPane.setMargin(label, new Insets(5));
                Separator separator = new Separator();
                GridPane.setHalignment(separator, HPos.CENTER);
                int rowTest = 0;
                gridPane.add(label, 0, rowTest++);
                gridPane.add(separator, 0, rowTest++);
                for (Test test : tests) {
                    if (test.getQuestions().size() != 0) {
                        Hyperlink hyperlink = new Hyperlink(test.getTestName());
                        hyperlink.setStyle("-fx-font-size: 14");
                        openTestInit(hyperlink, test);
                        gridPane.add(hyperlink, 0, rowTest++);
                    }
                }
                testsPane.add(gridPane, 0, rowSubject++);
            }
        }
    }
}