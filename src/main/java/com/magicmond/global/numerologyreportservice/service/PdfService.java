package com.magicmond.global.numerologyreportservice.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.magicmond.global.numerologyreportservice.entities.NumerologyReport;
import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import com.magicmond.global.numerologyreportservice.entities.PersonalityTrait;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.awt.Color;

@Service
public class PdfService {

    public byte[] createNumerologyPdf(NumerologyReport report, OrderInfo orderInfo) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 54, 54);
        PdfWriter.getInstance(document, out);

        document.open();

        // 1. Branding Header
        Font brandingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.GRAY);
        Paragraph branding = new Paragraph("MAGICMOND GLOBAL", brandingFont);
        branding.setAlignment(Element.ALIGN_RIGHT);
        document.add(branding);

        // 2. Report Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(41, 128, 185));
        Paragraph title = new Paragraph("NUMEROLOGY PROFILE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(20);
        title.setSpacingAfter(10);
        document.add(title);

        // 3. User Details Box
        addSectionHeader(document, "Personal Details");
        document.add(new Paragraph("Name: " + report.getReportMetadata().getUserName()));
        document.add(new Paragraph("Date of Birth: " + orderInfo.dob));
        document.add(new Paragraph("Calculation Date: " + report.getReportMetadata().getCalculationDate()));
        addSeparator(document);

        // 4. Life Path Section
        addSectionHeader(document, "Life Path Number: " + report.getLifePath().getNumber());
        document.add(new Paragraph(report.getLifePath().getDescription()));
        addSeparator(document);

        // 5. Personality Traits (Iterating List)
        addSectionHeader(document, "Personality Insights");
        for (PersonalityTrait trait : report.getPersonalityTraits()) {
            Paragraph p = new Paragraph();
            p.add(new Chunk(trait.getTrait().toUpperCase() + ": ", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            p.add(new Chunk(trait.getExplanation(), FontFactory.getFont(FontFactory.HELVETICA)));
            p.setSpacingAfter(5);
            document.add(p);
        }
        addSeparator(document);

        // 6. Career Insights
        addSectionHeader(document, "Career & Professional Guidance");
        document.add(new Paragraph("Recommended Fields: " + String.join(", ", report.getCareerInsights().getRecommendedFields())));
        document.add(new Paragraph("Core Strengths: " + String.join(", ", report.getCareerInsights().getStrengths())));

        Paragraph advice = new Paragraph("Advice: " + report.getCareerInsights().getAdvice());
        advice.setFont(FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE));
        document.add(advice);
        addSeparator(document);

        // 7. Luck Factors
        addSectionHeader(document, "Favorable Factors");
        document.add(new Paragraph("Lucky Numbers: " + report.getLuckFactors().getLuckyNumbers().toString()));
        document.add(new Paragraph("Lucky Colors: " + String.join(", ", report.getLuckFactors().getLuckyColors())));
        document.add(new Paragraph("Favorable Days: " + String.join(", ", report.getLuckFactors().getFavorableDays())));

        document.close();
        return out.toByteArray();
    }

    private void addSectionHeader(Document doc, String text) throws DocumentException {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(52, 73, 94));
        Paragraph p = new Paragraph(text.toUpperCase(), headFont);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        doc.add(p);
    }

    private void addSeparator(Document doc) throws DocumentException {
        Paragraph separator = new Paragraph("__________________________________________________________________________",
                FontFactory.getFont(FontFactory.HELVETICA, 10, Color.LIGHT_GRAY));
        separator.setSpacingAfter(10);
        doc.add(separator);
    }
}