package com.bptn.forms;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Consumer;

public abstract class BaseForm {
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    // Alert box to show errors
    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    // Info Box to show information
    protected void showInfo(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    // Method to show MFXGenericDialog
     protected void showDialog (Stage stage, Pane parent, String title, String message, String dialogType, Consumer<String> response) {
        Platform.runLater(() -> {
             dialogContent = MFXGenericDialogBuilder.build()
                    .setContentText(message)
                    .makeScrollable(true)
                    .get();
             dialog = MFXGenericDialogBuilder.build(dialogContent)
                    .toStageDialogBuilder()
                    .initOwner(stage)
                    .initModality(Modality.APPLICATION_MODAL)
                    .setDraggable(true)
                    .setTitle(title)
                    .setOwnerNode(parent)
                    .setScrimPriority(ScrimPriority.WINDOW)
                    .setScrimOwner(true)
                    .get();

            MFXButton confirmButton = new MFXButton("Confirm");
            MFXButton cancelButton = new MFXButton("Cancel");

            confirmButton.setOnAction(event -> {
                // Perform confirm action
                response.accept("Confirmed");
                dialog.close();
            });

            cancelButton.setOnAction(event -> dialog.close());

            dialogContent.addActions(
                    Map.entry(confirmButton, event -> {response.accept("Confirmed"); dialog.close();}),
                    Map.entry(cancelButton, event -> dialog.close())
            );

            switch (dialogType) {
                case "info":
                    dialogContent.setHeaderIcon(new MFXFontIcon("fas-circle-info", 18));
                    dialogContent.setHeaderText("Information");
                    break;
                case "warning":
                    dialogContent.setHeaderIcon(new MFXFontIcon("fas-circle-exclamation", 18));
                    dialogContent.setHeaderText("Warning");
                    break;
                case "error":
                    dialogContent.setHeaderIcon(new MFXFontIcon("fas-circle-xmark", 18));
                    dialogContent.setHeaderText("Error");

                    break;
                default:
                    dialogContent.setHeaderIcon(null);
                    dialogContent.setHeaderText("Dialog");
                    break;
            }

            dialogContent.setMaxSize(600, 300);
            convertDialogTo("mfx-" + dialogType + "-dialog");
            dialog.showDialog();
        });
    }

    private void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null)
            dialogContent.getStyleClass().add(styleClass);
    }

    // Will be implemented in controllers that display progress indicators
    protected abstract void toggleSpinner (boolean visible);
}