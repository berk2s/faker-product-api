package com.berk2s.talent.productapi.utils;

import com.berk2s.talent.productapi.web.exceptions.CSVParsingException;
import com.berk2s.talent.productapi.web.models.CSVTemplate;
import com.berk2s.talent.productapi.web.models.ErrorDesc;
import com.berk2s.talent.productapi.web.models.FormattedCSVRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
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
                            .withDelimiter(';')
                            .withQuote(null));

            List<FormattedCSVRecord> formattedRecords = new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();

            List<CSVRecord> records = csvParser.getRecords();

            ForkJoinPool pool = new ForkJoinPool(200);

            pool.submit(() ->
                    records.parallelStream().forEach(csvRecord -> {
                        if (csvRecord.size() >= csvParser.getHeaderMap().size()) {
                            try {
                                FormattedCSVRecord formattedCSVRecord = new FormattedCSVRecord();
                                formattedCSVRecord.setProductName(csvRecord.get(CSVTemplate.PRODUCT_NAME.getCsvText()).replace("\"", ""));
                                formattedCSVRecord.setProductId(csvRecord.get(CSVTemplate.PRODUCT_ID.getCsvText()).replace("\"", ""));
                                formattedCSVRecord.setListingPrice(Integer.parseInt(csvRecord.get(CSVTemplate.LISTING_PRICE.getCsvText()).replace("\"", "")));
                                formattedCSVRecord.setSalePrice(Integer.parseInt(csvRecord.get(CSVTemplate.SALE_PRICE.getCsvText()).replace("\"", "")));
                                formattedCSVRecord.setDiscount(Integer.parseInt(csvRecord.get(CSVTemplate.DISCOUNT.getCsvText()).replace("\"", "")));
                                formattedCSVRecord.setBrand(csvRecord.get(CSVTemplate.BRAND.getCsvText()).replace("\"", ""));
                                formattedCSVRecord.setDescription(csvRecord.get(CSVTemplate.DESCRIPTION.getCsvText()).replace("\"", ""));

                                String imageVal = csvRecord.get(CSVTemplate.IMAGES.getCsvText());

                                if (!imageVal.matches("-?(0|[1-9]\\\\d*)")) {
                                    String mutatedImage = imageVal
                                            .replace("\"\"", "\"")
                                            .replace("\"[", "[")
                                            .replace("]\"", "]");

                                    String[] images = objectMapper.readValue(mutatedImage, String[].class);

                                    Arrays.stream(images).parallel().forEach(imageUrl -> {
                                        try {
                                            log.info("Connection is preparing...");
                                            URL url = new URL(imageUrl);
                                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                            httpURLConnection.setRequestMethod("HEAD");

                                            if (httpURLConnection.getResponseCode() == 200) {
                                                formattedCSVRecord.getImages().add(imageUrl);
                                            }
                                        } catch (IOException e) {
                                            log.warn("Something went wrong {}", e.getMessage());
                                            throw new RuntimeException(e.getMessage());
                                        }
                                    });
                                }

                                formattedRecords.add(formattedCSVRecord);
                            } catch (JsonProcessingException e) {
                                log.warn("Error while parsing cvs images field {}", e.getMessage());
                                throw new CSVParsingException(ErrorDesc.CSV_PARSING_ERROR.getDesc());
                            }
                        }
                    })).get();

            return formattedRecords;
        } catch (Exception e) {
            log.warn("Error while parsing csv file {}", e.getMessage());
            throw new CSVParsingException(ErrorDesc.CSV_PARSING_ERROR.getDesc());
        }
    }

}
