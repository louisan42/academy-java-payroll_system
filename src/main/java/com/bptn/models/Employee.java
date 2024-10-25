package com.bptn.models;

import jakarta.persistence.*;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;


@Entity
@Table(name = "\"Employees\"")
public class Employee implements Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Employee () {
    }

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public int getId () {
        return id;
    }

    public String getEmail () {
        return email;
    }

    public String getGender () {
        return gender;
    }

    public int getDepartmentId () {
        return departmentId;
    }

    public String getStartDate () {
        return DateFormat.getDateInstance().format(startDate);
    }

    public String getSalary () {
        return salary;
    }

    public URL getAvatarUrl () {
        return avatarUrl;
    }

    private String email;
    private String gender;

    public Employee (String firstName, String lastName, String email, String gender, int departmentId, Date startDate, String salary, URL avatarUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
        this.startDate = startDate;
        this.salary = salary;
        this.avatarUrl = avatarUrl;
    }

    @Column(name = "department_id")
    private int departmentId;
    @Column(name = "start_date")
    private Date startDate;
    private String salary;
    @Column(name = "avatar_url")
    private URL avatarUrl;
    /**
     * @return 
     */
    @Override
    public String getFirstName () {
        return firstName;
    }

    /**
     * @return 
     */
    @Override
    public String getLastName () {
        return lastName;
    }

    /**
     * @param firstName 
     */
    @Override
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName 
     */
    @Override
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
}
