package com.bptn;

import java.io.InputStream;
import java.net.URL;

public class Utils {

    private Utils() {}
    public static URL loadURL(String path) {
        return App.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return Utils.class.getResourceAsStream(name);
    }



}
