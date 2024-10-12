
package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import com.bptn.Utils;
import com.bptn.constants.AppConstants;
import com.bptn.forms.BaseForm;
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
            String pass = Utils.verifyPassword(passwordField.getText());
            System.out.println(pass);
            App.switchScene("view/primary");
        }


  }
}
