package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}