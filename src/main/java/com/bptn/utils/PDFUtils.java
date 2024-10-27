package com.bptn.utils;

import com.bptn.App;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.Objects;


public class PDFUtils {

    private PDDocument document;
    private  int posX = 25;
    private  int posY = 760;
    private  int lineSpacing = 12;

    public  void createPayStub(String employeeId) {
        // Create a paystub for the employee

        try  {
            this.document = new PDDocument();
            float scale = 1f;
            String fileName = System.getenv("HOME") + "/Documents/"+"PayStub_" + employeeId + ".pdf";
            PDPage page = createPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            printHeader(contentStream, posX, posY);





            contentStream.close();

            document.save(fileName);
            document.close();


        } catch (SecurityException e) {
            System.out.println("Security Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

    private PDPage createPage () {
        PDPage page = new PDPage();
        page.setMediaBox(PDRectangle.LETTER);
        return page;
    }
    private  void writeTextOnNewLine(PDPageContentStream contentStream, int posX, int posY, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(posX, posY);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void printHeader(PDPageContentStream contentStream, int posX, int posY) throws IOException {
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        String imgPath = Objects.requireNonNull(App.class.getResource("static/newlogo.png")).getPath();
        System.out.println("path: "+imgPath);
        PDImageXObject pdLogo = PDImageXObject.createFromFile(imgPath , document);
        contentStream.drawImage(pdLogo, -20, posY-140, 220, 220);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
        contentStream.newLineAtOffset(posX, posY);
        contentStream.showText("EARNINGS STATEMENT");
        contentStream.endText();
        posX += 180;
        contentStream.moveTo(posX, posY+8);
        contentStream.lineTo(posX, posY- 50);
        contentStream.stroke();
//            contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
        posX += 10;
        posY -= 10;
        writeTextOnNewLine(contentStream, posX, posY, "Obsidi Academy By BPTN Inc.");
        posY -= lineSpacing + 6;
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, posX, posY, "1234 Main Street");
        posY -= lineSpacing;
        writeTextOnNewLine(contentStream, posX, posY, "Toronto, ON M1N3T5");

        // draw rectangle box with light gray background
        posY = 650;
        posX = 380;
        contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f);
        contentStream.addRect(posX, posY, 200, 100);
        contentStream.fill();
    }
}
