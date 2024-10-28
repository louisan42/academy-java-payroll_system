package com.bptn.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "\"Statement\"")
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "amount")
    private Double amount;

    public Double getAmount () {
        return amount;
    }

    public void setAmount (Double amount) {
        this.amount = amount;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Salary getSalary () {
        return salary;
    }

    public void setSalary (Salary salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate () {
        return startDate;
    }

    public void setStartDate (LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate () {
        return endDate;
    }

    public void setEndDate (LocalDate endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee () {
        return employee;
    }

    public void setEmployee (Employee employee) {
        this.employee = employee;
    }

    public LocalDate getIssueDate () {
        return issueDate;
    }

    public void setIssueDate (LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "From: " + startDate + " to: " + endDate;
    }

}