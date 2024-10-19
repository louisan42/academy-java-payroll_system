package com.bptn.models;


public interface Person {
    // Get the first name of the person
    String getFirstName();

    // Get the last name of the person
    String getLastName();

    // Get the full name of the person (first name + last name)
    default String getFullName(){
        return getFirstName() + " " + getLastName();
    };

    // Set the first name of the person
    void setFirstName(String firstName);

    // Set the last name of the person
    void setLastName(String lastName);


}

