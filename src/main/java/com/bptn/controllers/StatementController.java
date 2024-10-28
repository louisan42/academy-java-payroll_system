package com.bptn.controllers;

import com.bptn.forms.BaseForm;
import com.bptn.models.Employee;
import com.bptn.models.Salary;
import com.bptn.models.Statement;
import com.bptn.services.DBManager;
import com.bptn.services.StateManager;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class StatementController extends BaseForm {
    public AnchorPane centerPane;
    public MFXTextField employeeID;
    public MFXTextField fullName;
    public MFXDatePicker endDate;
    public MFXDatePicker startDate;
    public MFXComboBox<Salary> salary;
    public MFXTextField amount;
    public MFXTextField editAmount;
    public MFXDatePicker editEndDate;
    public MFXDatePicker editStartDate;
    public MFXComboBox<Salary> editSalary;
    public MFXTextField searchId;
    public MFXTextField employeeName;

    private Employee employee;

    public void initialize () {
        salary.setItems(FXCollections.observableArrayList(StateManager.getSalaries()));
        editSalary.setItems(FXCollections.observableArrayList(StateManager.getSalaries()));
        salary.getSelectionModel().selectFirst();
        editSalary.getSelectionModel().selectFirst();
        salary.setDisable(true);
        fullName.setDisable(true);

        toggleSpinner(true);


    }


    /**
     * @param visible
     */
    @Override
    protected void toggleSpinner (boolean visible) {
        editSalary.setDisable(visible);
        editStartDate.setDisable(visible);
        editEndDate.setDisable(visible);
        editAmount.setDisable(visible);
        employeeName.setDisable(visible);
    }

    public void updateStatement (ActionEvent actionEvent) {
        if (!editAmount.getText().isEmpty() && editStartDate.getValue() != null && editEndDate.getValue() != null && editSalary.getValue() != null) {
            DBManager dbManager = new DBManager(DBManager.getSessionFactory());
            Statement statement = dbManager.getStatementById(Integer.parseInt(searchId.getText()));
            statement.setAmount(Double.parseDouble(editAmount.getText()));
            statement.setStartDate(editStartDate.getValue());
            statement.setEndDate(editEndDate.getValue());
            statement.setSalary(editSalary.getValue());
            dbManager.updateStatement(statement);
        }
    }

    public void searchStatement (ActionEvent actionEvent) {
        if (!searchId.getText().isEmpty()) {
            DBManager dbManager = new DBManager(DBManager.getSessionFactory());
            Statement statement = dbManager.getStatementById(Integer.parseInt(searchId.getText()));
            editAmount.setText(statement.getAmount().toString());
            editAmount.setDisable(false);
            editStartDate.setValue(statement.getStartDate());
            editStartDate.setDisable(false);
            editEndDate.setValue(statement.getEndDate());
            editEndDate.setDisable(false);
            editSalary.setValue(statement.getSalary());
            editSalary.setDisable(false);
            searchId.setDisable(true);
            employeeName.setText(statement.getEmployee().getFullName());
        }

    }

    public void addStatement (ActionEvent actionEvent) {
        if (!employeeID.getText().isEmpty() && !fullName.getText().isEmpty() && startDate.getValue() != null && endDate.getValue() != null && salary.getValue() != null && !amount.getText().isEmpty()) {
            DBManager dbManager = new DBManager(DBManager.getSessionFactory());
            Statement statement = new Statement();
            statement.setEmployee(employee);
            statement.setStartDate(startDate.getValue());
            statement.setEndDate(endDate.getValue());
            statement.setSalary(employee.getSalary());
            statement.setAmount(Double.parseDouble(amount.getText()));
            statement.setIssueDate(endDate.getValue().minusDays(1));
            dbManager.addStatement(statement);


        }
    }

    public void searchEmployee (ActionEvent actionEvent) {
        if (!employeeID.getText().isEmpty()) {
            DBManager dbManager = new DBManager(DBManager.getSessionFactory());
            this.employee = dbManager.getEmployeeById(Integer.parseInt(employeeID.getText()));
            fullName.setText(employee.getFullName());
            salary.setText(employee.getSalary().getName());

        }

    }
}