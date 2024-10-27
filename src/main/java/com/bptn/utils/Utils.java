package com.bptn.utils;

import com.bptn.App;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

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

    public static void createPayStub(String employeeId) {
        // Create a paystub for the employee
        try (PDDocument document = new PDDocument()) {
            String fileName = System.getenv("HOME") + "PayStub_" + employeeId + ".pdf";
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = new PDType1Font(FontName.HELVETICA);


            document.save(fileName);


        } catch (SecurityException e) {
            System.out.println("Security Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }



}
