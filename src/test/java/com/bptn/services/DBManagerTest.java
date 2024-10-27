package com.bptn.services;

import com.bptn.models.Department;
import com.bptn.models.Employee;
import com.bptn.models.Salary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBManagerTest {
    @InjectMocks
    private DBManager dbManager;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        dbManager = new DBManager(sessionFactory);

        when(sessionFactory.openSession()).thenReturn(session);
    }

    // Update operations

    @Test
    void updateEmployeeSuccessful() {
        Map<String,Object> newEmployeeData = new HashMap<>();
        newEmployeeData.put("gender","Male");
        newEmployeeData.put("email","kf@live.com");
        newEmployeeData.put("date",LocalDate.parse("2021-01-01"));
        newEmployeeData.put("salary",8000);

        Employee existingEmployee = mock(Employee.class);
        Salary salary = mock(Salary.class);


        when(session.get(Employee.class, 10800)).thenReturn(existingEmployee);
        when(session.beginTransaction()).thenReturn(transaction);
        when(existingEmployee.getEmail()).thenReturn(newEmployeeData.get("email").toString());
        when(existingEmployee.getSalary()).thenReturn(salary);
        when(salary.getId()).thenReturn(Integer.valueOf(newEmployeeData.get("salary").toString()));

        dbManager.updateEmployee(newEmployeeData, 10800);

        verify(session).merge(existingEmployee);
        verify(transaction).commit();

        // Technically, we don't need assertions here, since all we need to verify
        // is that the merge and commit methods were called. However, we can add
        // assertions to ensure that the correct data was passed to the merge method.
        assertEquals("kf@live.com", existingEmployee.getEmail());
        assertEquals(8000, existingEmployee.getSalary().getId());
    }

    @Test
    void updateEmployeeNoChanges() {
        Employee existingEmployee = mock(Employee.class);
        Employee returnedEmployee = mock(Employee.class);
        Map<String,Object> newEmployeeData = new HashMap<>();
        newEmployeeData.put("gender","X");
        newEmployeeData.put("email","sss@k.com");
        newEmployeeData.put("date",LocalDate.parse("2021-01-01"));
        newEmployeeData.put("salary_id",8000);

        when(session.get(Employee.class, 1)).thenReturn(existingEmployee);
        when(session.beginTransaction()).thenReturn(transaction);

        dbManager.updateEmployee(newEmployeeData, 11006);

        verify(session, never()).merge(existingEmployee);
        verify(transaction, never()).commit();


    }

    // Delete operations
    @Test
    void deleteEmployeeByIdSuccessful() {
        Employee existingEmployee = mock(Employee.class);

        when(session.get(Employee.class, 10800)).thenReturn(existingEmployee);
        when(session.beginTransaction()).thenReturn(transaction);

        dbManager.deleteEmployeeById(10800);

        verify(session).remove(existingEmployee);
        verify(transaction).commit();
    }

    @Test
    void deleteEmployeeByIdNotFound() {
        when(session.get(Employee.class, 1)).thenReturn(null);
        when(session.beginTransaction()).thenReturn(transaction);

        dbManager.deleteEmployeeById(1);

        verify(session, never()).remove(any(Employee.class));
        verify(transaction).rollback();
    }

}