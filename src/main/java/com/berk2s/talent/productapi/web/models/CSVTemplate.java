package com.berk2s.talent.productapi.web.models;

import lombok.Getter;

@Getter
public enum CSVTemplate {
    CATEGORY("category"),
    SUB_CATEGORY("subcategory"),
    NAME("name"),
    CURRENT_PRICE("current_price"),
    RAW_PRICE("raw_price"),
    CURRENCY("currency"),
    DISCOUNT("discount"),
    LIKES_COUNT("likes_count"),
    THUMBNAIL("variation_0_thumbnail"),
    THUMBNAIL_IMAGE("variation_1_image"),
    IMAGE_URL("image_url"),
    SKU("model");

    private final String csvText;

    CSVTemplate(String csvText) {
        this.csvText = csvText;
    }
}
