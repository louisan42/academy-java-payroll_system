package com.bptn.models;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Manager\"")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Department getDepartment () {
        return department;
    }

    public void setDepartment (Department department) {
        this.department = department;
    }

    public Employee getEmployee () {
        return employee;
    }

    public void setEmployee (Employee employee) {
        this.employee = employee;
    }

}