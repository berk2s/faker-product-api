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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            csvParser.forEach(csvRecord -> {
                if(csvRecord.size() >= csvParser.getHeaderMap().size()) {
                    try {
                        FormattedCSVRecord formattedCSVRecord = new FormattedCSVRecord();
                        formattedCSVRecord.setProductName(csvRecord.get(CSVTemplate.PRODUCT_NAME.getCsvText()));
                        formattedCSVRecord.setProductId(csvRecord.get(CSVTemplate.PRODUCT_ID.getCsvText()));
                        formattedCSVRecord.setListingPrice(Integer.parseInt(csvRecord.get(CSVTemplate.LISTING_PRICE.getCsvText())));
                        formattedCSVRecord.setSalePrice(Integer.parseInt(csvRecord.get(CSVTemplate.SALE_PRICE.getCsvText())));
                        formattedCSVRecord.setDiscount(Integer.parseInt(csvRecord.get(CSVTemplate.DISCOUNT.getCsvText())));
                        formattedCSVRecord.setBrand(csvRecord.get(CSVTemplate.BRAND.getCsvText()));
                        formattedCSVRecord.setDescription(csvRecord.get(CSVTemplate.DESCRIPTION.getCsvText()));


                        String imageVal = csvRecord.get(CSVTemplate.IMAGES.getCsvText());

                        if (!imageVal.matches("-?(0|[1-9]\\\\d*)")) {
                            String mutatedImage = imageVal
                                    .replace("\"\"", "\"")
                                    .replace("\"[", "[")
                                    .replace("]\"", "]");

                            String[] images = objectMapper.readValue(mutatedImage, String[].class);

                            Arrays.stream(images).forEach(imageUrl -> {
                                formattedCSVRecord.getImages().add(imageUrl);
                            });
                        }

                        formattedRecords.add(formattedCSVRecord);
                    } catch (JsonProcessingException e) {
                        log.warn("Error while parsing cvs images field {}", e.getMessage());
                        throw new CSVParsingException(ErrorDesc.CSV_PARSING_ERROR.getDesc());
                    }
                }
            });

            return formattedRecords;
        } catch (IOException exception) {
            log.warn("Error while parsing csv file {}", exception.getMessage());
            throw new CSVParsingException(ErrorDesc.CSV_PARSING_ERROR.getDesc());
        }
    }

}
