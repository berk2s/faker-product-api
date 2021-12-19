package com.berk2s.talent.productapi.web.models;

import lombok.Getter;

@Getter
public enum ErrorDesc {
    CSV_PARSING_ERROR("Error while parsing csv"),
    CSV_FILE_NOT_FOUND("CSV file not found"),
    PRODUCT_NOT_FOUND("Product not found");

    private final String desc;

    ErrorDesc(String desc) {
        this.desc = desc;
    }
}
