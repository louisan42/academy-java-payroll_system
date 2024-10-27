package com.bptn.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "\"Employee\"")
public class Employee implements Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;

    public Employee (String firstName, String lastName,String gender, LocalDate startDate, Department department, Salary salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.startDate = startDate;
        this.department = department;
        this.salary = salary;
    }

    @Column(name = "last_name", nullable = false, length = 256)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "avatar_url", length = 128)
    private String avatarUrl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    public Employee () {

    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }
    @Override
    public String getFirstName () {
        return firstName;
    }
    @Override
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }
    @Override
    public String getLastName () {
        return lastName;
    }
    @Override
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getGender () {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public LocalDate getStartDate () {
        return startDate;
    }

    public void setStartDate (LocalDate startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment () {
        return department;
    }

    public void setDepartment (Department department) {
        this.department = department;
    }

    public String getAvatarUrl () {
        return avatarUrl;
    }

    public void setAvatarUrl (String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Salary getSalary () {
        return salary;
    }

    public void setSalary (Salary salary) {
        this.salary = salary;
    }



    /**
     * @param employeeFields a map of the fields to compare
     * @return true if the employees are equal
     */

    public boolean compareFields (Map<String, Object> employeeFields) {
        if (!this.getGender().equals(employeeFields.get("gender"))
                || !this.getEmail().equals(employeeFields.get("email"))
                || !this.getStartDate().equals(employeeFields.get("startDate"))
                || !this.getSalary().getId().equals(employeeFields.get("salary_id")))
//                || !this.getDepartment().getName().equals(employee.getDepartment().getName())
//                || !this.getDepartment().getLocation().equals(employee.getDepartment().getLocation()))
        {
            return false;
        }
        return true;

    }
}