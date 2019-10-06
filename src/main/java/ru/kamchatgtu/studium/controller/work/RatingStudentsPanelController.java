package ru.kamchatgtu.studium.controller.work;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.Comparator;

public class RatingStudentsPanelController {

    @FXML
    private GridPane ratingStudentsPane;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TableView<User> ratingStudentsTable;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> fioRatingColumn;
    @FXML
    private TableColumn<User, String> groupColumn;
    @FXML
    private TableColumn<User, Number> ratingColumn;

    @FXML
    public void initialize() {
        initIdColumn();
        initFioRatingColumn();
        initGroupColumn();
        initRatingColumn();
        ratingStudentsPane.visibleProperty().addListener(observable -> {
            RatingAsync resultsAsync = new RatingAsync();
            resultsAsync.execute();
        });
    }


    private void initIdColumn() {
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(ratingStudentsTable.getItems().indexOf(param.getValue()) + 1 + ""));
    }

    private void initFioRatingColumn() {
        fioRatingColumn.setCellValueFactory(param -> param.getValue().fioProperty());
    }

    private void initGroupColumn() {
        groupColumn.setCellValueFactory(param -> param.getValue().getGroup().groupNameProperty());
    }

    private void initRatingColumn() {
        ratingColumn.setCellValueFactory(param -> param.getValue().ratingProperty());
    }

    private class RatingAsync extends AsyncTask<Void, Void, ObservableList<User>> {
        @Override
        public void onPreExecute() {
            progressIndicator.setVisible(true);
            ratingStudentsTable.setVisible(false);
        }

        @Override
        public ObservableList<User> doInBackground(Void... voids) {
            ObservableList<User> users = new RestConnection().getRestUser().getStudents();
            for (User user : users) {
                ObservableList<ResultTest> resultTests = new RestConnection().getRestResultTest().getByUser(user.getIdUser());
                int avg = 0;
                int count = resultTests.size();
                for (ResultTest resultTest : resultTests) {
                    avg += resultTest.getMark();
                }
                if (count != 0)
                    user.setRating(avg / count);
                else
                    user.setRating(0);
            }
            users.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return Double.compare(o2.ratingProperty().get(), o1.ratingProperty().get());
                }
            });
            return users;
        }

        @Override
        public void onPostExecute(ObservableList<User> rating) {
            progressIndicator.setVisible(false);
            ratingStudentsTable.setVisible(true);
            ratingStudentsTable.setItems(rating);
        }

        @Override
        public void progressCallback(Void... voids) {

        }
    }
}
