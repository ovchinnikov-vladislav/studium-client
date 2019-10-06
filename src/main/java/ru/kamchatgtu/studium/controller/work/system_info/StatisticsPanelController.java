package ru.kamchatgtu.studium.controller.work.system_info;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Date;

public class StatisticsPanelController {

    @FXML
    private GridPane statisticsPane, subjectsPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab facultiesTab, subjectsTab, usersTab, themesAndQuestionsTab, testsTab, resultsTab;

    @FXML
    public void initialize() {
        initTabPane();
        initSubjectLineChart();
    }

    private void initSubjectLineChart() {
        Axis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        LineChart<Date, Number> numberNumberLineChart = new LineChart<>(x, y);
        numberNumberLineChart.setTitle("График изменений в таблицах subject и direction_subject");
        XYChart.Series series = new XYChart.Series();
        ObservableList data = FXCollections.observableArrayList();
        numberNumberLineChart.setCreateSymbols(false);
        for (int i = 0; i < 10000000; i+=100000)
            data.add(new XYChart.Data<>(new Date(i).toString(), Math.sin(i)));
        series.setData(data);
        numberNumberLineChart.getData().add(series);
        subjectsPane.add(numberNumberLineChart, 0, 2);
    }

    private void initTabPane() {
        tabPane.setRotateGraphic(true);

        Label l = new Label("Факультеты");
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        facultiesTab.setGraphic(stp);

        l = new Label("Дисциплины");
        l.setRotate(90);
        stp = new StackPane(new Group(l));
        stp.setRotate(90);
        subjectsTab.setGraphic(stp);

        l = new Label("Пользователи");
        l.setRotate(90);
        stp = new StackPane(new Group(l));
        stp.setRotate(90);
        usersTab.setGraphic(stp);

        l = new Label("Темы и вопросы");
        l.setRotate(90);
        stp = new StackPane(new Group(l));
        stp.setRotate(90);
        themesAndQuestionsTab.setGraphic(stp);

        l = new Label("Тесты");
        l.setRotate(90);
        stp = new StackPane(new Group(l));
        stp.setRotate(90);
        testsTab.setGraphic(stp);

        l = new Label("Результаты");
        l.setRotate(90);
        stp = new StackPane(new Group(l));
        stp.setRotate(90);
        resultsTab.setGraphic(stp);

        tabPane.setTabMinHeight(110);
        tabPane.setTabMaxHeight(110);
    }
}
