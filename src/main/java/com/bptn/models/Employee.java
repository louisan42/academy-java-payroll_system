package com.bptn.models;

import java.net.URL;
import java.util.Date;

public class Employee implements Person {

    private int id;
    private String firstname;
    private String lastName;
    private String email;
    private String gender;
    private int departmentId;
    private Date startDate;
    private String salary;
    private URL avatarUrl;
    /**
     * @return 
     */
    @Override
    public String getFirstName () {
        return "";
    }

    /**
     * @return 
     */
    @Override
    public String getLastName () {
        return "";
    }

    /**
     * @param firstName 
     */
    @Override
    public void setFirstName (String firstName) {

    }

    /**
     * @param lastName 
     */
    @Override
    public void setLastName (String lastName) {

    }
}
