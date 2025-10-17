package com.example.pdf;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("pdf")
@PageTitle("Pdf Printer")
@Menu(order = 1, icon = "vaadin:pdf", title = "Pdf Printer")
public class PdfView extends VerticalLayout {

    @Autowired
    public PdfView(PdfService pdfService) {
        Button printButton = new Button("Stampa PDF", event -> {
            try {
                pdfService.createAndPrintPdf("Testo di esempio", "output.pdf");
                Notification.show("PDF stampato e log salvato!");
            } catch (Exception e) {
                Notification.show("Errore: " + e.getMessage());
            }
        });
        add(printButton);
    }
}