package ru.kamchatgtu.studium.engine;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static javafx.animation.Animation.Status;

public class Animation {
    private TranslateTransition translateTransition = new TranslateTransition();
    private boolean isAnimation;

    public boolean getAnimation() { return isAnimation; }

    public void animationLabelTransitionStart(Label label, double X, double Y) {
        label.setStyle("-fx-text-fill: #2292e4; -fx-font-size: 18");
        System.out.println(translateTransition.getStatus());
        translateTransition.setDuration(Duration.millis(150));
        translateTransition.setByX(X);
        translateTransition.setByY(Y);
        translateTransition.setNode(label);
        translateTransition.play();
        if (translateTransition.getStatus() == Status.STOPPED) {
            isAnimation = true;
        }
    }

    public void animationLabelTransitionEnd(Label label, double X, double Y) {
        label.setStyle("-fx-text-fill: grey; -fx-font-size: 20");
        System.out.println(translateTransition.getStatus());
        translateTransition.setDuration(Duration.millis(150));
        translateTransition.setByX(X);
        translateTransition.setByY(Y);
        translateTransition.setNode(label);
        translateTransition.play();
        if (translateTransition.getStatus() == Status.STOPPED) {
            isAnimation = false;
        }
    }
}
