package com.sarvesh.codetest2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sarvesh.codetest2.model.CompanyRecord;
import com.sarvesh.codetest2.repository.CompanyRecordRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
public class RecordService {
    @Autowired
    private CompanyRecordRepository companyRecordRepository;

    public void processAndStoreRecords(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String eventType = null;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^[A-Z][0-9]+.*")) {
                    eventType = line;
                } else if (line.trim().isEmpty()) {
                    eventType = null;
                } else if (eventType != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        CompanyRecord record = new CompanyRecord();
                        record.setCompanyName(parts[0]);
                        record.setCompanyNumber(parts[1]);
                        record.setEventType(eventType);
                        record.setEventDate(LocalDate.parse(parts[2]));
                        record.setUniqueIdentifier(parts[3]);
                        companyRecordRepository.save(record);
                    }
                }
            }
        } catch (IOException e) {
            // Handle exceptions, log errors, or throw custom exceptions
            e.printStackTrace();
            // You can create a custom exception class for better error handling
            // Example: throw new CustomRecordProcessingException("Error processing records", e);
        }
    }
}
