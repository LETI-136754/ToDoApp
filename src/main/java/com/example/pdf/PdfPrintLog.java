package com.example.pdf;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PdfPrintLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private LocalDateTime printTime;

    // Costruttori, getter e setter
    public PdfPrintLog() {}

    public PdfPrintLog(String fileName, LocalDateTime printTime) {
        this.fileName = fileName;
        this.printTime = printTime;
    }

    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public LocalDateTime getPrintTime() { return printTime; }
    public void setPrintTime(LocalDateTime printTime) { this.printTime = printTime; }
}