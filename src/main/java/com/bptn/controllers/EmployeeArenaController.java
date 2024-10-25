package com.bptn.controllers;

import com.bptn.App;
import com.bptn.forms.FormUtils;
import com.bptn.models.Employee;
import com.bptn.services.DBManager;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for the EmployeeArena view
 *
 * @author Louis Amoah-Nuamah
 */
public class EmployeeArenaController implements FormUtils {
    public StackPane gridBackground;
    public GridPane parentGrid;
    @FXML
    private MFXPaginatedTableView<Employee> tableView;
    private MFXProgressSpinner progressSpinner;

    private DashboardController dashboardController;

    @FXML
    public void initialize () {
        progressSpinner = new MFXProgressSpinner();
        progressSpinner.setRadius(30);
        progressSpinner.setVisible(false);
        parentGrid.getChildren().add(progressSpinner);
        GridPane.setConstraints(progressSpinner, 0, 0, 3, 3, HPos.CENTER, VPos.CENTER);
        setupTable();
        loadData();
        setupRowSelectionListener();
    }

    /**
     * Set the dashboard controller to share resources
     *
     * @param dashboardController the dashboard controller
     */
    public void setDashboardController (DashboardController dashboardController) {
        this.dashboardController = dashboardController;

    }

    /**
     * Method to handle row selection events
     *
     * @param observable observable map of employees and their keys
     * @param oldValue old value of the observable map
     * @param newValue new value of the observable map
     * @throws IOException if an error occurs while loading the view
     */
    private void changed (ObservableValue<? extends ObservableMap<Integer, Employee>> observable, ObservableMap<Integer, Employee> oldValue, ObservableMap<Integer, Employee> newValue) throws IOException {
        if (newValue != null) {
            Employee emp = newValue.values().stream().findFirst().orElse(null);
            assert emp != null;
            System.out.println("Clicked on " + emp.getId());
            loadSingleEmployeeView(emp);
        }
    }
    /**
     * Set up the table view
     */
    private void setupTable () {
        MFXTableColumn<Employee> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Employee::getId));
        MFXTableColumn<Employee> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Employee::getFullName));
        MFXTableColumn<Employee> emailColumn = new MFXTableColumn<>("Email", false, Comparator.comparing(Employee::getEmail));
        MFXTableColumn<Employee> startDate = new MFXTableColumn<>("Start Date", false, Comparator.comparing(Employee::getStartDate));
        MFXTableColumn<Employee> departmentId = new MFXTableColumn<>("Department ID", false, Comparator.comparing(Employee::getDepartmentId));
        MFXTableColumn<Employee> salary = new MFXTableColumn<>("Salary", false, Comparator.comparing(Employee::getSalary));


        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getId));
        nameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getFullName));
        emailColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getEmail));
        startDate.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getStartDate));
        departmentId.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getDepartmentId));
        salary.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getSalary));

        emailColumn.setMinWidth(300.0);
        nameColumn.setMinWidth(200.0);

        tableView.getTableColumns().addAll(idColumn, nameColumn, emailColumn, startDate, departmentId, salary);

    // Setup filter for the table view
        tableView.getFilters().setAll(new IntegerFilter<>("ID", Employee::getId), new StringFilter<>("Name", Employee::getFullName), new StringFilter<>("Email", Employee::getEmail), new IntegerFilter<>("Department Id", Employee::getDepartmentId), new StringFilter<>("Salary level", Employee::getSalary));

        tableView.getSelectionModel().setAllowsMultipleSelection(false);
        // Set up pagination
        tableView.setCurrentPage(0);
        tableView.setRowsPerPage(20);

    }

    /**
     * Load the data from the database and populate the table view
     */
    private void loadData () {
        // Show the spinner before starting the data fetching process
        toggleSpinner(true);

        // Fetch the data in a background thread to avoid blocking the UI
        new Thread(() -> {
            List<Employee> employees = DBManager.getAllEmployees();

            // Update the table view on the JavaFX Application Thread
            Platform.runLater(() -> {
                tableView.setItems(FXCollections.observableArrayList(employees));

                // Hide the spinner after the data is loaded
                toggleSpinner(false);
            });
        }).start();
    }

    /**
     * Set up a listener to handle row selection events.
     *
     * @throws RuntimeException if an IOException occurs from the method changed
     */
    private void setupRowSelectionListener () {
        tableView.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            toggleSpinner(true);
            Platform.runLater(() -> {
                try {
                    changed(observable, oldValue, newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    toggleSpinner(false);
                }
            });
        });
    }

    /**
     * Load the selected single employee view
     *
     * @param emp the employee to display
     * @throws IOException if an error occurs while loading the view
     */
    public void loadSingleEmployeeView (Employee emp) throws IOException {
        if (dashboardController == null) {
            System.out.println("DashboardController is null");
            return;
        }


        FXMLLoader loader = new FXMLLoader(App.class.getResource("views/employee.fxml"));
        Parent employeeView = loader.load();

        // Get the controller and set the employee details
        EmployeeController employeeController = loader.getController();
        employeeController.setDetails(emp);

        // Get the stackPane from the dashboardController
        StackPane stackPane = dashboardController.getCenterContent();

        // Add the employeeView to the stackPane
        Platform.runLater(() -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(employeeView);
            stackPane.layout();
        });

    }

    /**
     * @param visible boolean value to set visibility of spinner
     */
    @Override
    public void toggleSpinner (boolean visible) {

        Platform.runLater(() -> {
            gridBackground.setVisible(!visible);
            progressSpinner.setVisible(visible);
        });

    }
}

