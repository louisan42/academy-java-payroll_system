package com.bptn.controllers;

import com.bptn.models.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;

public class EmployeeController {
    public MFXTextField fullName;
    public MFXTextField gender;
    public MFXTextField email;
    public MFXTextField startDate;
    public MFXTextField salary;
    public MFXTextField departmentName;
    public MFXTextField mangerName;
    public MFXButton saveButton;
    public MFXButton deleteButton;
    public MFXTextField departmentLocation;

    public void setDetails (Employee employee) {
        // Set the details of the employee
        fullName.setText(employee.getFullName());
        gender.setText(employee.getGender());
        email.setText(employee.getEmail());
        startDate.setText(employee.getStartDate());
        salary.setText(employee.getSalary());

        // Disable the text fields
        toggleDisableFields(true);



    }

    private void toggleDisableFields (boolean disable) {
        fullName.setDisable(disable);
        gender.setDisable(disable);
        email.setDisable(disable);
        startDate.setDisable(disable);
        salary.setDisable(disable);

    }

    public void saveChanges () {
    }

    public void deleteEmployee () {
    }
}
