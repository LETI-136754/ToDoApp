package com.example.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PdfService {

    @Autowired
    private PdfPrintLogRepository logRepository;

    public File createAndPrintPdf(String text, String fileName) throws IOException {
        File file = new File(fileName);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(text);
                contentStream.endText();
            }

            document.save(file);
        }

        // Salva log nel database
        PdfPrintLog log = new PdfPrintLog(fileName, LocalDateTime.now());
        logRepository.save(log);

        // Qui puoi aggiungere la logica per inviare il file alla stampante se necessario

        return file;
    }
}