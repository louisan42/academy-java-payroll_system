
package com.bptn.controllers;

import java.io.IOException;
import java.util.Optional;

import com.bptn.App;

import com.bptn.constants.AppConstants;
import com.bptn.forms.BaseForm;
import com.bptn.models.User;
import com.bptn.services.AuthenticatorService;
import com.bptn.services.DBManager;
import com.bptn.services.StateManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LoginController extends BaseForm {

    public TextField usernameField;
    public BorderPane loginBorderPane;
    public AnchorPane loginAnchorPane;
    public Label userNameLabel;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button loginButton;
    public PieChart departmentPieChart;
    public ProgressIndicator progressIndicator;
    public StackPane loginStackPane;
    public StackPane centerContent;
    AuthenticatorService authService = new AuthenticatorService();

    @FXML
  private void login() throws IOException {
        final String username = usernameField.getText();
        final String password = passwordField.getText();
        if(username.isEmpty() || password.isEmpty()){
            this.showAlert(Alert.AlertType.ERROR, AppConstants.INPUT_ERROR,AppConstants.USERNAME_PASSWORD_REQUIRED);

        } else {
            toggleSpinner(true);
            Task<User> fetchUserTask = new Task<>(){

                /**
                 * @return User object
                 */
                @Override
                protected User call () {

                    return new DBManager(DBManager.getSessionFactory()).readUserByUsername(username);
                }
            };

            fetchUserTask.setOnSucceeded(workerStateEvent -> {
                toggleSpinner(false);
                Optional<User> user = Optional.ofNullable(fetchUserTask.getValue());
                if (user.isPresent()) {
                    final String passwordHash = user.get().getPasswordHash();
                    if (authService.verifyPassword(password,passwordHash)){
                        StateManager.setUser(user.get());
                        try {
                            App.switchScene("views/dashboard");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        this.showAlert(Alert.AlertType.ERROR,AppConstants.INVALID_CREDENTIALS,AppConstants.INVALID_CREDENTIALS);
                    }

                } else {
                    this.showAlert(Alert.AlertType.ERROR,AppConstants.USER_NOT_FOUND,AppConstants.USER_NOT_FOUND);
                }

            });
            // In case we get any error retrieving user from DB
            fetchUserTask.setOnFailed(workerStateEvent -> {
                toggleSpinner(false);
                this.showAlert(Alert.AlertType.ERROR,"Error",fetchUserTask.getException().getMessage());
            });

            new Thread(fetchUserTask).start();
        }


  }

    /**
     * Set visibility of components.
     * Useful during Task handling
     * @param visible boolean value to set visibility
     */
    @Override
    public void toggleSpinner (boolean visible) {
        progressIndicator.setVisible(visible);
        loginButton.setDisable(visible);
        usernameField.setDisable(visible);
        passwordField.setDisable(visible);
    }


}
