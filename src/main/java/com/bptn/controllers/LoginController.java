
package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import com.bptn.Utils;
import com.bptn.constants.AppConstants;
import com.bptn.forms.BaseForm;
import com.bptn.models.AppUser;
import com.bptn.models.Person;
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

    @FXML
  private void login() throws IOException {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            BaseForm.showAlert(Alert.AlertType.ERROR, AppConstants.INPUT_ERROR,AppConstants.USERNAME_PASSWORD_REQUIRED);

        } else {
            Person user;
            String passwordHash;

            if (App.users.containsKey(usernameField.getText())){
                user = App.users.get(usernameField.getText());
            }

            App.switchScene("view/primary");
        }


  }
}
