package com.bptn.controllers;

import com.bptn.forms.BaseForm;
import com.bptn.models.Employee;
import com.bptn.models.Salary;
import com.bptn.services.DBManager;
import com.bptn.utils.PDFUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeController extends BaseForm {
    public MFXTextField fullName;
    public MFXTextField gender;
    public MFXTextField email;
    public MFXTextField startDate;
    public MFXComboBox<Salary> salary;
    public MFXTextField departmentName;
    public MFXTextField mangerName;
    public MFXButton saveButton;
    public MFXButton deleteButton;
    public MFXTextField departmentLocation;
    public StackPane logoContainer;
    public ImageView avatarContainer;
    public Label employeeIdLbl;
    public MFXButton generateReport;
    public AnchorPane centerPane;
    private  ChangeListener<String> changeListener;
    DBManager dbManager;

    public void editEmployee () {
        // Enable the text fields
        toggleDisableFields(false);
    }

    @FXML
    public void initialize() {
        changeListener= (observable, oldValue, newValue) -> saveButton.setDisable(false);

        fullName.setDisable(true);
        departmentName.setDisable(true);
        salary.setDisable(true);
        mangerName.setDisable(true);
        departmentLocation.setDisable(true);
        // Initially disable the save button
        saveButton.setDisable(true);

        dbManager = new DBManager(DBManager.getSessionFactory());
        List<Salary> allSalaries = dbManager.getAllSalaries();
        salary.setItems(FXCollections.observableArrayList(allSalaries.stream().sorted().collect(Collectors.toList())));
    }

    public void setProfilePicture(String imageUrl) {
        // Create an Image object
        Image image = new Image(imageUrl);

        // Set the Image object to the ImageView
        avatarContainer.setImage(image);

        // Optionally, set properties to make it look like a profile picture
        avatarContainer.setFitHeight(100);
        avatarContainer.setFitWidth(100);
        avatarContainer.setPreserveRatio(true);
        avatarContainer.setSmooth(true);
        avatarContainer.setCache(true);



    }

    public void setDetails (Employee employee) {
        // Set the details of the employee
        fullName.setText(employee.getFullName());
        gender.setText(employee.getGender());
        email.setText(employee.getEmail());
        startDate.setText(employee.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        salary.setText(employee.getSalary().getName());
        departmentName.setText(employee.getDepartment().getName());
        mangerName.setText(employee.getDepartment().getManager().getEmployee().getFullName());
        departmentLocation.setText(employee.getDepartment().getLocation());
        String empId = employeeIdLbl.getText().concat(employee.getId().toString());
        employeeIdLbl.setText(empId);
        setProfilePicture(employee.getAvatarUrl());

        // Disable the text fields
        toggleDisableFields(true);
        email.textProperty().addListener(changeListener);
        gender.textProperty().addListener(changeListener);
        startDate.textProperty().addListener(changeListener);
        salary.textProperty().addListener(changeListener);

    }

    public void toggleDisableFields (boolean disable) {
//        fullName.setDisable(disable);
        gender.setDisable(disable);
        email.setDisable(disable);
        startDate.setDisable(disable);
        salary.setDisable(disable);
//        departmentName.setDisable(disable);
//        mangerName.setDisable(disable);
//        departmentLocation.setDisable(disable);


    }

    public void saveChanges () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Map<String,Object> newEmployeeData = new HashMap<>();
        newEmployeeData.put("gender",gender.getText());
        newEmployeeData.put("email",email.getText());
        newEmployeeData.put("date",LocalDate.parse(startDate.getText(),formatter));
        newEmployeeData.put("salary_id",salary.getValue().getId());
        int employeeId = Integer.parseInt(employeeIdLbl.getText().replace("ID:", ""));

        dbManager.updateEmployee(newEmployeeData, employeeId);

    }

    public void deleteEmployee () {
        int employeeId = Integer.parseInt(employeeIdLbl.getText().replace("ID:", ""));
        dbManager.deleteEmployeeById(employeeId);
    }

    public void toggleDisableFields (MouseEvent mouseEvent) {
        // Get the source of the event
        Node source = (Node) mouseEvent.getSource();
        toggleDisableFields(false);
    }

    public void generateReport (ActionEvent actionEvent) {
        Stage stage = (Stage) generateReport.getScene().getWindow();
        this.showGenericDialog(stage, centerPane, "Generate Report", "Do you want to generate a report for this employee?",
                "warning", response ->{
                        System.out.println("Response " + response);
                        if (response.equalsIgnoreCase("Confirmed")) {
                            PDFUtils statement = new PDFUtils();
                            statement.createPayStub(employeeIdLbl.getText().replace("ID:", ""));
                        }
                });
//        this.showInfo("Report generated successfully", "Report", "Report Generated");

    }

    /**
     * @param visible boolean value to set visibility of spinner
     */
    @Override
    protected void toggleSpinner (boolean visible) {

    }
}
