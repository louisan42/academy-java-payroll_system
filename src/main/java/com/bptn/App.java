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
        loginScene = new Scene(loadFXML("login"), 350, 200);
        mainScene = new Scene(loadFXML("login"), 700, 580);
        App.stage = stage;
        stage.setScene(loginScene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    public static void switchScene(String rootFxml) throws IOException {
        stage.setScene(mainScene);
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