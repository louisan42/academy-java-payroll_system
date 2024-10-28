package com.bptn.utils;

import com.bptn.App;
import com.bptn.models.Statement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;


public class PDFUtils {

    private PDDocument document;
    private final float START_X = 25f;
    private final float START_Y = 740f;
    private float posX = START_X;
    private float posY = START_Y;
    private float lineSpacing = 12f;
    private final Statement statement;

    public PDFUtils (Statement statement) {
        this.statement = statement;
    }

    public void createPayStub () {
        // Create a paystub for the employee

        try {
            this.document = new PDDocument();
            float scale = 1f;
            String fileName = System.getenv("HOME") + "/Documents/" + "PayStub_" + statement.getEmployee().getId() + ".pdf";
            PDPage page = createPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            printHeader(contentStream, posX, posY);
            printBody(contentStream);
            printFooter(contentStream);
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

    private void writeTextOnNewLine (PDPageContentStream contentStream, float posX, float posY, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(posX, posY);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void printHeader (PDPageContentStream contentStream, float posX, float posY) throws IOException {
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        String imgPath = Objects.requireNonNull(App.class.getResource("static/newlogo.png")).getPath();
        System.out.println("path: " + imgPath);
        PDImageXObject pdLogo = PDImageXObject.createFromFile(imgPath, document);
        contentStream.drawImage(pdLogo, -20, posY - 140, 220, 220);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
        contentStream.newLineAtOffset(posX, posY);
        contentStream.showText("EARNINGS STATEMENT");
        contentStream.endText();
        posX += 180f;
        contentStream.moveTo(posX, posY + 8f);
        contentStream.lineTo(posX, posY - 50f);
        contentStream.stroke();
//            contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
        posX += 10f;
        posY -= 10f;
        writeTextOnNewLine(contentStream, posX, posY, "Obsidi Academy By BPTN Inc.");
        posY -= lineSpacing + 6;
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, posX, posY, "1234 Main Street");
        posY -= lineSpacing;
        writeTextOnNewLine(contentStream, posX, posY, "Toronto, ON M1N3T5");

        // draw rectangle box with light gray background
        posY = START_Y - 60f;
        posX = START_X + 375f;
        contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f);
        contentStream.addRect(posX, posY, 200, 60);
        contentStream.fill();
        contentStream.setNonStrokingColor(0f, 0f, 0f);
        contentStream.setFont(font, 8);
        posX += 10f;
        posY = START_Y - 20f;
        writeTextOnNewLine(contentStream, posX, posY, "Statement ID: " + statement.getId());
        posY -= lineSpacing + 3f;
        writeTextOnNewLine(contentStream, posX, posY, "Pay Period: " + statement.getStartDate() + " - " + statement.getEndDate());
        posY -= lineSpacing + 3f;
        writeTextOnNewLine(contentStream, posX, posY, "Issue Date: " + statement.getIssueDate());
    }

    private void printFooter (PDPageContentStream contentStream) throws IOException {

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_OBLIQUE), 10);
        posY = 50f + lineSpacing;
        posX = START_X;
        contentStream.moveTo(posX, posY);
        contentStream.lineTo(posX + 565f, posY);
        contentStream.stroke();
        posY -= lineSpacing;
        writeTextOnNewLine(contentStream, posX, posY, "This is a computer generated statement and does not require a signature.");
        posY -= lineSpacing;
        writeTextOnNewLine(contentStream, posX, posY, "For any questions or concerns, please contact the HR department.");
    }

    private void printBody (PDPageContentStream contentStream) throws IOException {

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        currencyFormatter.setCurrency(Currency.getInstance(Locale.CANADA));

        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        PDFont boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        contentStream.setFont(boldFont, 10);
        posY = 400f;
        posX = START_X;
        float secondMarginX = 120f;
        float thirdMarginX = 300f;
        float fourthMarginX = 370f;
        writeTextOnNewLine(contentStream, posX, posY, "Name: ");
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, secondMarginX, posY, statement.getEmployee().getFullName());
        contentStream.setFont(boldFont, 10);
        writeTextOnNewLine(contentStream, thirdMarginX, posY, "Department: ");
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, fourthMarginX, posY, statement.getEmployee().getDepartment().getName());
        posY -= lineSpacing + 4f;
        contentStream.setFont(boldFont, 10);
        writeTextOnNewLine(contentStream, posX, posY, "Employee ID: ");
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, secondMarginX, posY, statement.getEmployee().getId().toString());
        contentStream.setFont(boldFont, 10);
        writeTextOnNewLine(contentStream, thirdMarginX, posY, "Location: ");
        contentStream.setFont(font, 10);
        writeTextOnNewLine(contentStream, fourthMarginX, posY, statement.getEmployee().getDepartment().getLocation());
        posY -= lineSpacing + 4f;
        contentStream.setFont(boldFont, 10);
        writeTextOnNewLine(contentStream, posX, posY, "Pay Rate: ");
        contentStream.setFont(font, 10);
        double netPay = statement.getSalary().getAmount();
        writeTextOnNewLine(contentStream, secondMarginX, posY, currencyFormatter.format(netPay) + " Annual");
        contentStream.setFont(boldFont, 10);
        posY -= lineSpacing + 20f;
        writeTextOnNewLine(contentStream, fourthMarginX + 50f, posY, "Total Earnings: ");
        contentStream.setFont(font, 10);
        double totalEarnings = statement.getAmount();
        writeTextOnNewLine(contentStream, fourthMarginX + 125f, posY, currencyFormatter.format(totalEarnings));


    }
}
