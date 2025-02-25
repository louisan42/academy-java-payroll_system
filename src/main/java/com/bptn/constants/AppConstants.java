package com.bptn.constants;

import com.bptn.App;

public class AppConstants {
    public static final String INPUT_ERROR ="Input Error";
    public static final String USERNAME_PASSWORD_REQUIRED ="Username and Password are required";
    public static final String USERS_CSV_FILE = App.class.getResource("static/users.csv").getPath();
    public static final String USER_NOT_FOUND = "User cannot be found";
    public static final  String INVALID_CREDENTIALS = "Invalid credentials";
}
