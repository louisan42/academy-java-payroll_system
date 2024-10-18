package com.bptn.services;

import com.bptn.models.Person;

public class StateManager {
    private static Person user;

    public static Person getUser () {
        return user;
    }

    public static void setUser (Person user) {
        StateManager.user = user;
    }



}
