
package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import com.bptn.Utils;
import com.bptn.constants.AppConstants;
import com.bptn.forms.BaseForm;
import com.bptn.models.AppUser;
import com.bptn.models.Person;
import com.bptn.services.AuthenticatorService;
import com.bptn.services.StateManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LoginController {

    public TextField usernameField;
    public BorderPane loginBorderPane;
    public AnchorPane loginAnchorPane;
    public Label userNameLabel;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button loginButton;
    AuthenticatorService authService = new AuthenticatorService();

    @FXML
  private void login() throws IOException {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            BaseForm.showAlert(Alert.AlertType.ERROR, AppConstants.INPUT_ERROR,AppConstants.USERNAME_PASSWORD_REQUIRED);

        } else {
            String passwordHash;
            String userKey = usernameField.getText();

            if (App.users.containsKey(userKey)){
                passwordHash = App.users.get(userKey).getPasswordHash();
                if (authService.verifyPassword(passwordField.getText(),passwordHash)){
                    StateManager.setUser(App.users.get(userKey));
                    App.switchScene("view/dashboard");
                } else {
                    BaseForm.showAlert(Alert.AlertType.ERROR,AppConstants.INVALID_CREDENTIALS,AppConstants.INVALID_CREDENTIALS);
                }

            } else {
                BaseForm.showAlert(Alert.AlertType.ERROR,AppConstants.USER_NOT_FOUND,AppConstants.USER_NOT_FOUND);
            }


        }


  }
}
