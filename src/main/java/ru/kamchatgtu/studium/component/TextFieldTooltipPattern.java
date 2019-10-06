package ru.kamchatgtu.studium.component;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.regex.Pattern;

public class TextFieldTooltipPattern {
    public static void initField(TextField textField, Tooltip tooltip, Pattern pattern) {
        tooltip.setWrapText(true);
        textField.focusedProperty().addListener(observable -> tooltip.hide());
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) {
                initShowTooltip(textField, tooltip, oldValue);
            }
        });
    }

    public static void initShowTooltip(TextField textField, Tooltip tooltip, String oldValue) {
        if (!(textField instanceof PasswordField))
            textField.setText(oldValue);
        tooltip.show(textField,
                textField.getScene().getWindow().getX() + textField.getLayoutX() + textField.getWidth() + 10,
                textField.getScene().getWindow().getY() + textField.getLayoutY() + textField.getHeight());
    }
}
