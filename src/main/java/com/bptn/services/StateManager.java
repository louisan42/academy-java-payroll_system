package com.bptn.services;

import com.bptn.models.Department;
import com.bptn.models.Person;
import com.bptn.models.Salary;
import com.bptn.models.User;

import java.util.List;

public class StateManager {

    private static DBManager dbManager;

    private StateManager () {}
    private static User user;
    private static List<Salary> salaries;
    private static List<Department> departments;

    public static Person getUser () {
        return user;
    }

    public static void setUser (User user) {
        StateManager.user = user;
    }

    public static void clearUser () {
        StateManager.user = null;
    }

    public static boolean isLoggedIn () {
        return user != null;
    }

    public static boolean isManager () {
        return user != null && user.getIsManager();
    }

    public static List<Department> getDepartments () {
        return departments;
    }

    public static void setDepartments (List<Department> departments) {
        StateManager.departments = departments;
    }

    public static List<Salary> getSalaries () {
        return salaries;
    }

    public static void setSalaries (List<Salary> salaries) {
        StateManager.salaries = salaries;
    }


}
