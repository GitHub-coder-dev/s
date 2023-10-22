package com.sarvesh.codetest2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sarvesh.codetest2.model.CompanyRecord;
import com.sarvesh.codetest2.repository.CompanyRecordRepository; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    private final CompanyRecordRepository companyRecordRepository; 

    @Autowired
    public FileUploadController(CompanyRecordRepository companyRecordRepository) {
        this.companyRecordRepository = companyRecordRepository;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file for upload.");
        }

        try {
            List<CompanyRecord> records = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 5) {
                        String companyName = parts[0];
                        String companyNumber = parts[1];
                        String eventType = parts[2];
                        LocalDate eventDate = LocalDate.parse(parts[3]);
                        String uniqueIdentifier = parts[4];

                        CompanyRecord record = new CompanyRecord(companyName, companyNumber, eventType, eventDate, uniqueIdentifier);
                        records.add(record);
                    }
                }
            }

            companyRecordRepository.saveAll(records);
            return ResponseEntity.ok("File uploaded and records saved.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error uploading and saving records: " + e.getMessage());
        }
    }
}
