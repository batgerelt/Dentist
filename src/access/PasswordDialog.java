package access;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PasswordDialog extends Dialog<String> {
    private PasswordField passwordField;

    public PasswordDialog(String name) {
        setTitle("Нууц үг солих");
        setHeaderText(name + " хэрэглэгчийн нууц үг оруулна уу.");

        ButtonType passwordButtonType = new ButtonType("Хадгалах", ButtonData.OK_DONE);
        ButtonType passwordButtonCancel = new ButtonType("Цуцлах", ButtonData.NO);
        getDialogPane().getButtonTypes().addAll(passwordButtonCancel,passwordButtonType);

        passwordField = new PasswordField();
        passwordField.setPromptText("Нууц үг");

        HBox hBox = new HBox();
        hBox.getChildren().add(passwordField);
        hBox.setPadding(new Insets(20));

        HBox.setHgrow(passwordField, Priority.ALWAYS);

        getDialogPane().setContent(hBox);

        Platform.runLater(() -> passwordField.requestFocus());

        setResultConverter(dialogButton -> {
            if (dialogButton == passwordButtonType) {
                return passwordField.getText();
            }
            return null;
        });
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }
}