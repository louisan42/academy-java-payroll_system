package com.bptn.controllers;

import java.io.IOException;

import com.bptn.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("view/secondary");
    }
}
