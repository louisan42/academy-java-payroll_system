package com.bptn.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Salary\"")
public class Salary implements Comparable<Salary> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @OneToMany(mappedBy = "salary")
    private Set<Employee> employees = new LinkedHashSet<>();

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getAmount () {
        return amount;
    }

    public void setAmount (Double amount) {
        this.amount = amount;
    }

    public Set<Employee> getEmployees () {
        return employees;
    }

    public void setEmployees (Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * @param salary the salary to compare
     * @return the comparison result
     */
    @Override
    public int compareTo (Salary salary) {
        return this.name.compareTo(salary.getName());
    }
    // needed for the table view
    @Override
    public String toString () {
        return name;
    }
}