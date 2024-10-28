package com.bptn.utils;

import com.bptn.models.Statement;
import com.bptn.services.DBManager;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFUtilsTest {

    @Test
    void createPayStub () {

        String employeeId = "12345";
        String filePath = System.getenv("HOME") + "/Documents/PayStub_" + employeeId + ".pdf";
        DBManager dbManager = new DBManager(DBManager.getSessionFactory());
        Statement statement = dbManager.getStatementById(10703);

        PDFUtils PDStatement = new PDFUtils(statement);
        PDStatement.createPayStub();

        File file = new File(filePath);
        assertTrue(file.exists());

        file.delete();
    }
}