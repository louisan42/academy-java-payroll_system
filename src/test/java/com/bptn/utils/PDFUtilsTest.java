package com.bptn.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFUtilsTest {

    @Test
    void createPayStub () {

        String employeeId = "12345";
        String filePath = System.getenv("HOME") + "/Documents/PayStub_" + employeeId + ".pdf";

        PDFUtils statement = new PDFUtils();
        statement.createPayStub(employeeId);

        File file = new File(filePath);
        assertTrue(file.exists());

        // Clean up
        //file.delete();
    }
}