package com.bptn.forms;

import javafx.scene.control.Alert;

public interface FormUtils {

    // Alert box to show errors
     static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    // Info Box to show information
     static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    // will be implemented in controllers that displays progress indicators
    void toggleSpinner (boolean visible);
}
