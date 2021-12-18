package com.berk2s.talent.productapi.bootstrap;

import com.berk2s.talent.productapi.domain.Brand;
import com.berk2s.talent.productapi.domain.Product;
import com.berk2s.talent.productapi.repository.BrandRepository;
import com.berk2s.talent.productapi.repository.ProductRepository;
import com.berk2s.talent.productapi.utils.CSVReader;
import com.berk2s.talent.productapi.web.models.ErrorDesc;
import com.berk2s.talent.productapi.web.models.FormattedCSVRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.countAll() == 0) {
            loadInitialData();
        }
    }

    public void loadInitialData() {
        try {
            List<FormattedCSVRecord> formattedCSVRecords = CSVReader.readCSV(new FileInputStream("src/main/resources/data.csv"));

            formattedCSVRecords.forEach(formattedCSVRecord -> {
                Brand brand;
                if(!brandRepository.existsByBrandName(formattedCSVRecord.getBrand())) {
                    brand = new Brand();
                    brand.setBrandName(formattedCSVRecord.getBrand());

                    brandRepository.save(brand);
                } else {
                    brand = brandRepository.findByBrandName(formattedCSVRecord.getBrand()).get();
                }

                int rangeMin = 0;
                int rangeMax = 5;
                Random random = new Random();

                double rating = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
                rating = Math.round(rating * 100);
                rating = rating / 100;

                Product product = new Product();
                product.setProductName(formattedCSVRecord.getProductName());
                product.setProductExternalId(formattedCSVRecord.getProductId());
                product.setDescription(formattedCSVRecord.getDescription());
                product.setListingPrice(formattedCSVRecord.getListingPrice());
                product.setSalePrice(formattedCSVRecord.getSalePrice());
                product.setDiscount(formattedCSVRecord.getDiscount());
                product.setBrand(brand);
                product.setRating(rating);

                formattedCSVRecord.getImages().forEach(product::addImage);
            });

        } catch (FileNotFoundException exception) {
            log.warn("CSV File was not found");
            throw new RuntimeException(ErrorDesc.CSV_FILE_NOT_FOUND.getDesc());
        }
    }
}
