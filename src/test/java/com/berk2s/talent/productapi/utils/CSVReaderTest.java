package com.berk2s.talent.productapi.utils;

import com.berk2s.talent.productapi.web.models.FormattedCSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVReaderTest {

    @Test
    void readCSV() throws FileNotFoundException {
        List<FormattedCSVRecord> formattedCSVRecords = CSVReader.readCSV(new FileInputStream("src/main/resources/data.csv"));

        assertThat(formattedCSVRecords.size())
                .isGreaterThan(0);
    }
}