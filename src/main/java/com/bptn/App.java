package com.bptn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene loginScene;
    private static Scene mainScene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        final double loginWidth = 650.0;
        final double loginHeight = 460.0;
        loginScene = new Scene(loadFXML("view/login"));
        mainScene = new Scene(loadFXML("view/primary"), 700, 580);
        App.stage = stage;
        stage.setScene(loginScene);
        // lock window resizing
        stage.setMinWidth(loginWidth);
        stage.setMaxWidth(loginWidth);
        stage.setMinHeight(loginHeight);
        stage.setMaxHeight(loginHeight);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    public static void switchScene(String rootFxml) throws IOException {
        stage.setScene(mainScene);
        // unlock window resizing
        stage.setMinWidth(700);
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMinHeight(580);
        stage.setMaxHeight(Double.MAX_VALUE);
        setRoot(rootFxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}