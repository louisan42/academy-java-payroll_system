package com.bptn.services;

public interface Authenticator {

    public String hashPassword(String password);
    public boolean verifyPassword(String password,String encodedPassword);
}
