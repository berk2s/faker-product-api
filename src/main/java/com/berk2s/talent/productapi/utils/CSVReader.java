package com.berk2s.talent.productapi.utils;

import com.berk2s.talent.productapi.web.exceptions.CSVParsingException;
import com.berk2s.talent.productapi.web.models.CSVTemplate;
import com.berk2s.talent.productapi.web.models.ErrorDesc;
import com.berk2s.talent.productapi.web.models.FormattedCSVRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public final class CSVReader {

    public static List<FormattedCSVRecord> readCSV(InputStream is) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            CSVParser csvParser = new CSVParser(bufferedReader,
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withDelimiter(',')
                            .withNullString(""));

            List<FormattedCSVRecord> formattedRecords = new ArrayList<>();

            List<CSVRecord> records = csvParser.getRecords();

            records.forEach(csvRecord -> {
                if (csvRecord.size() >= csvParser.getHeaderMap().size()) {
                    FormattedCSVRecord formattedCSVRecord = new FormattedCSVRecord();

                    formattedCSVRecord.setProductName(new Faker().commerce().productName());
                    formattedCSVRecord.setListingPrice(csvRecord.get(CSVTemplate.CURRENT_PRICE.getCsvText()));
                    formattedCSVRecord.setSalePrice(csvRecord.get(CSVTemplate.RAW_PRICE.getCsvText()));
                    formattedCSVRecord.setDiscount(csvRecord.get(CSVTemplate.DISCOUNT.getCsvText()));
                    formattedCSVRecord.setBrand(csvRecord.get(CSVTemplate.CATEGORY.getCsvText()));
                    formattedCSVRecord.setDescription(csvRecord.get(CSVTemplate.NAME.getCsvText()));
                    formattedCSVRecord.setProductId(csvRecord.get(CSVTemplate.SKU.getCsvText()));

                    String productImage = csvRecord.get(CSVTemplate.IMAGE_URL.getCsvText());
                    String thumbnail = csvRecord.get(CSVTemplate.THUMBNAIL.getCsvText());
                    String variation = csvRecord.get(CSVTemplate.THUMBNAIL_IMAGE.getCsvText());
                    formattedCSVRecord.getImages().add(productImage);
                    formattedCSVRecord.getImages().add(thumbnail);
                    formattedCSVRecord.getImages().add(variation);

                    formattedRecords.add(formattedCSVRecord);

                }
            });

            return formattedRecords;
        } catch (Exception exception) {
            log.warn("Error while parsing csv file {}", exception.getMessage());
            throw new CSVParsingException(ErrorDesc.CSV_PARSING_ERROR.getDesc());
        }
    }

}
