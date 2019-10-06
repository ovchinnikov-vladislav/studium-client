package ru.kamchatgtu.studium.controller.work;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

public class SystemInfoPanelController {

    @FXML
    private GridPane mainPanel;
   // @FXML
  //  private TabPane tabPane;
    @FXML
    private GridPane logsPane;
    @FXML
    private GridPane statisticsPane;

    @FXML
    public void initialize() {
        selectPanel(false, false);
        mainPanel.visibleProperty().addListener(observable -> {
//            tabPane.getSelectionModel().select(0);
            selectPanel(false, false);
            selectPanel(true, false);
        });
    }

    @FXML
    public void openLogsPane(Event event) {
        selectPanel(true, false);
    }

    @FXML
    public void openStatisticsPane(Event event) {
        selectPanel(false, true);
    }

    private void selectPanel(boolean isVisibleLogsPane, boolean isVisibleStatisticsPane) {
        if (logsPane != null)
            logsPane.setVisible(isVisibleLogsPane);
        if (statisticsPane != null)
            statisticsPane.setVisible(isVisibleStatisticsPane);
    }
}
