package com.bptn.controllers;

import com.bptn.App;
import com.bptn.forms.BaseForm;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.layout.ScalableContentPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for the Dashboard view
 *
 * @author Louis Amoah-Nuamah
 */
public class DashboardController extends BaseForm {


    public BorderPane mainPane;
    @FXML
    private ScalableContentPane centerContent;
    private MFXProgressSpinner progressSpinner;

    public BorderPane getMainPane () {
        return mainPane;
    }

    @FXML
    public void initialize() {
        progressSpinner = new MFXProgressSpinner();
        progressSpinner.setRadius(50);
        progressSpinner.setVisible(false);
//        centerContent.getChildren().add(progressSpinner);
        StackPane progressPane = new StackPane();
        progressPane.getChildren().add(progressSpinner);
        mainPane.setCenter(progressPane);
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

            ScalableContentPane pane = new ScalableContentPane();
            pane.setContent(employeeArenaView);
            mainPane.setCenter(pane);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToAdmin (ActionEvent actionEvent) {
        toggleSpinner(true);
        loadContent("views/statements.fxml");
        toggleSpinner(false);
    }

/**
 * method to load the content of the fxml file
 * from App resources and set it to the center of the dashboard
 * @param fxml  the fxml file to load
 * */
    public  void loadContent (String fxml) {
        try {
            Node content = FXMLLoader.load(App.class.getResource(fxml));
//            centerContent.getChildren().setAll(content);
//            centerContent.setContent(new StackPane());
//            centerContent.setContent(content);
            ScalableContentPane pane = new ScalableContentPane();
            pane.setContent(content);
            mainPane.setCenter(pane);

        } catch (IOException|IllegalArgumentException e) {
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
