package com.berk2s.talent.productapi.bootstrap;

import com.berk2s.talent.productapi.utils.CSVReader;
import com.berk2s.talent.productapi.web.models.ErrorDesc;
import com.berk2s.talent.productapi.web.models.FormattedCSVRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        loadInitialData();
    }

    protected void loadInitialData() {
        try {
            List<FormattedCSVRecord> formattedCSVRecords = CSVReader.readCSV(new FileInputStream("src/main/resources/data.csv"));

            formattedCSVRecords.forEach(formattedCSVRecord -> {
                log.info(formattedCSVRecord.toString());
            });

        } catch (FileNotFoundException exception) {
            log.warn("CSV File was not found");
            throw new RuntimeException(ErrorDesc.CSV_FILE_NOT_FOUND.getDesc());
        }
    }
}
