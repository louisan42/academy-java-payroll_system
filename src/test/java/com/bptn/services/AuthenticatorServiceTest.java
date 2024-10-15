package com.bptn.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorServiceTest {
    private AuthenticatorService authService = new AuthenticatorService();

    @Test
    void verifyPasswordTrueMatch () {
        String password = "password1234";
        String endcodedPassword = authService.hashPassword(password);

        boolean result = authService.verifyPassword(password,endcodedPassword);
        assertTrue(result,"Password String should match encoded password");
    }

    @Test
    void verifyPasswordFalseMatch () {
        String password = "password1234";
        String badPassword = "1234Password";
        String endcodedPassword = authService.hashPassword(password);

        boolean result = authService.verifyPassword(badPassword,endcodedPassword);
        assertFalse(result,"Password String should not match encoded password");
    }
}