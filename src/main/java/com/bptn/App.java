package com.bptn;
import com.bptn.constants.AppConstants;
import com.bptn.controllers.DashboardController;
import com.bptn.controllers.EmployeeArenaController;
import com.bptn.models.AppUser;
import com.bptn.services.CsvAppUserLoader;
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
import java.util.Map;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene mainScene;

    public static Stage getStage () {
        return stage;
    }

    private static Stage stage;
    public static Map<String, AppUser> users;
    private static final double width = 1520;
    private static final double height = 950;

    @Override
    public void start(Stage stage) throws IOException {

        // Load user database from csv file
        CsvAppUserLoader userDb = new CsvAppUserLoader();
        users = userDb.loadFile(AppConstants.USERS_CSV_FILE);

        //Link these two controllers to share resources
//        FXMLLoader dashboardLoader = loadFXML("views/dashboard");
//        Parent dashboardRoot = dashboardLoader.load();
//        DashboardController dashboardController = dashboardLoader.getController();
//
//        FXMLLoader employeeArenaLoader = loadFXML("views/employeeArena");
//        Parent employeeArenaRoot = employeeArenaLoader.load();
//
//        EmployeeArenaController employeeArenaController = employeeArenaLoader.getController();
////        employeeArenaLoader.setControllerFactory(empArena -> {
////            if (empArena == EmployeeArenaController.class) {
////                return new EmployeeArenaController(dashboardController);
////            } else {
////                try {
////                    return empArena.getDeclaredConstructor().newInstance();
////                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
////                         InvocationTargetException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        });
//        dashboardController.setEmployeeArenaController(employeeArenaController);
//        employeeArenaController.setDashboardController(dashboardController);

        // Ensure initialization happens after setting controllers
//        employeeArenaController.initialize();
//        dashboardController.initialize();


        Scene loginScene = new Scene(loadFXML("views/dashboard").load());
//        Scene loginScene = new Scene(employeeArenaRoot);


        App.stage = stage;
        stage.setScene(loginScene);

        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA) // Optional if you don't need JavaFX's default theme, still recommended though
                .themes(MaterialFXStylesheets.forAssemble(true)) // Adds the MaterialFX's default theme. The boolean argument is to include legacy controls
                .setDeploy(true) // Whether to deploy each theme's assets on a temporary dir on the disk
                .setResolveAssets(true) // Whether to try resolving @import statements and resources urls
                .build() // Assembles all the added themes into a single CSSFragment (very powerful class check its documentation)
                .setGlobal(); // Finally, sets the produced stylesheet as the global User-Agent stylesheet
        // lock window resizing
//        stage.setMinWidth(loginWidth);
//        stage.setMaxWidth(loginWidth);
//        stage.setMinHeight(loginHeight);
//        stage.setMaxHeight(loginHeight);
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

    public static void main(String[] args) {
        launch();
    }

}