package com.bptn.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "\"Department\"")
public class Department implements Comparable<Department> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "\"manager_Id\"")
    private Manager manager;

    @Column(name = "location", nullable = false, length = 10)
    private String location;

    @OneToMany(mappedBy = "department")
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

    public Manager getManager () {
        return manager;
    }

    public void setManager (Manager manager) {
        this.manager = manager;
    }

    public String getLocation () {
        return location;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public Set<Employee> getEmployees () {
        return employees;
    }

    public void setEmployees (Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * @param department 
     * @return
     */
    @Override
    public int compareTo (Department department) {
        if (!Objects.equals(this.id, department.getId())
            || !Objects.equals(this.name, department.getName())
            || !Objects.equals(this.location, department.getLocation())
        ) {
            return 1;
        }
        return 0;
    }
}