package com.berk2s.talent.productapi.web.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String desc) {
        super(desc);
    }
}
