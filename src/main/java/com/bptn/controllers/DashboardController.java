package com.bptn.controllers;

import com.bptn.App;
import com.bptn.forms.FormUtils;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for the Dashboard view
 *
 * @author Louis Amoah-Nuamah
 */
public class DashboardController implements FormUtils {


    @FXML
    private  StackPane centerContent;
    private MFXProgressSpinner progressSpinner;

    public StackPane getCenterContent () {
        return centerContent;
    }

    @FXML
    public void initialize() {
        progressSpinner = new MFXProgressSpinner();
        progressSpinner.setRadius(50);
        progressSpinner.setVisible(false);
        centerContent.getChildren().add(progressSpinner);
        showOverview();
    }
    @FXML
    public void showOverview () {
        toggleSpinner(true);
        loadContent("views/overview.fxml");
        toggleSpinner(false);
    }
    @FXML
    public void switchToEmployees(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("views/employeeArena.fxml"));
            Parent employeeArenaView = loader.load();

            // Get the controller and set the DashboardController
            EmployeeArenaController employeeArenaController = loader.getController();
            employeeArenaController.setDashboardController(this);

            centerContent.getChildren().setAll(employeeArenaView);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    @FXML
    public void switchToAdmin (ActionEvent actionEvent) {
    }

/**
 * method to load the content of the fxml file
 * from App resources and set it to the center of the dashboard
 * @param fxml  the fxml file to load
 * */
    public  void loadContent (String fxml) {
        try {
            Node content = FXMLLoader.load(App.class.getResource(fxml));
            centerContent.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set visibility of loading spinner.
     * @param visible boolean value to set visibility
     */
    @Override
    public void toggleSpinner (boolean visible) {
        Platform.runLater(() -> progressSpinner.setVisible(visible) );
    }
}
