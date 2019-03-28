package ru.kamchatgtu.studium.component;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class AutoSizeTextArea extends TextArea {
    public AutoSizeTextArea() {
        setWrapText(true);

        sceneProperty().addListener((observableNewScene, oldScene, newScene) -> {
            if (newScene != null) {
                applyCss();
                Node text = lookup(".text");

                prefHeightProperty().bind(Bindings.createDoubleBinding(() -> getFont().getSize() + 5 + text.getBoundsInLocal().getHeight(), text.boundsInLocalProperty()));
                text.boundsInLocalProperty().addListener((observableBoundsAfter, boundsBefore, boundsAfter) ->
                        Platform.runLater(() -> requestLayout()));
            }
        });
    }
}
