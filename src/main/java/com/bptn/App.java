package com.bptn;
import com.bptn.constants.AppConstants;
import com.bptn.models.AppUser;
import com.bptn.models.Person;
import com.bptn.services.CsvAppUserLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene mainScene;
    private static Stage stage;
    public static Map<String, AppUser> users;

    @Override
    public void start(Stage stage) throws IOException {
        final double loginWidth = 650.0;
        final double loginHeight = 460.0;

        // Load user database from csv file
        CsvAppUserLoader userDb = new CsvAppUserLoader();
        users = userDb.loadFile(AppConstants.USERS_CSV_FILE);

        Scene loginScene = new Scene(loadFXML("view/login"));
        mainScene = new Scene(loadFXML("view/dashboard"), 700, 580);
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