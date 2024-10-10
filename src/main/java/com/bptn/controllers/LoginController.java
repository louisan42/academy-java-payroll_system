
package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import javafx.fxml.FXML;

public class LoginController {

  @FXML
  private void switchToPrimary() throws IOException {
    App.switchScene("primary");
   // App.setRoot("primary");
  }
}
