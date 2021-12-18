package com.berk2s.talent.productapi.web.models;

import lombok.Getter;

@Getter
public enum CSVTemplate {
    PRODUCT_NAME("Product Name"),
    PRODUCT_ID("Product ID"),
    LISTING_PRICE("Listing Price"),
    SALE_PRICE("Sale Price"),
    DISCOUNT("Discount"),
    BRAND("Brand"),
    DESCRIPTION("Description"),
    IMAGES("Images");

    private final String csvText;

    CSVTemplate(String csvText) {
        this.csvText = csvText;
    }
}
