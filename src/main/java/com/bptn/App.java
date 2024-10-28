package com.bptn;
import com.bptn.constants.AppConstants;
import com.bptn.controllers.DashboardController;
import com.bptn.controllers.EmployeeArenaController;
import com.bptn.models.AppUser;
import com.bptn.models.Department;
import com.bptn.models.Salary;
import com.bptn.services.CsvAppUserLoader;
import com.bptn.services.DBManager;
import com.bptn.services.StateManager;
import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene mainScene;
    private static final Logger logger = Logger.getLogger(App.class.getName());


    public static Stage getStage () {
        return stage;
    }
    public static Logger getLogger () {
        return logger;
    }

    private static Stage stage;
    public static Map<String, AppUser> users;
    private static final double width = 1520;
    private static final double height = 950;

    @Override
    public void start(Stage stage) throws IOException {

        setStates();

        logger.info(App.class + ":Starting application");

        // Load user database from csv file
        CsvAppUserLoader userDb = new CsvAppUserLoader();
        users = userDb.loadFile(AppConstants.USERS_CSV_FILE);

        Scene loginScene = new Scene(loadFXML("views/login").load());
        mainScene = new Scene(loadFXML("views/dashboard").load());

        App.stage = stage;
        stage.setScene(loginScene);

        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA) // Optional if you don't need JavaFX's default theme, still recommended though
                .themes(MaterialFXStylesheets.forAssemble(true)) // Adds the MaterialFX's default theme. The boolean argument is to include legacy controls
                .setDeploy(true) // Whether to deploy each theme's assets on a temporary dir on the disk
                .setResolveAssets(true) // Whether to try resolving @import statements and resources urls
                .build() // Assembles all the added themes into a single CSSFragment (very powerful class check its documentation)
                .setGlobal(); // Finally, sets the produced stylesheet as the global User-Agent stylesheet

        stage.show();






    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml).load());
    }

    public static void switchScene(String rootFxml) throws IOException {
        stage.setScene(mainScene);
        // lock window resizing
        stage.setMinWidth(width);
        stage.setMaxWidth(width);
        stage.setMinHeight(height);
        stage.setMaxHeight(height);
        setRoot(rootFxml);
    }

    private static FXMLLoader loadFXML(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    private static void setStates() {
        // Set the states of the application

        // Set the salaries
        DBManager dbManager = new DBManager(DBManager.getSessionFactory());
        List<Salary> salaries = dbManager.getAllSalaries();
        StateManager.setSalaries(salaries);

        // Set the departments
        List<Department> departments = dbManager.getAllDepartments();
        StateManager.setDepartments(departments);
    }

    public static void main(String[] args) {
        launch();
    }

}