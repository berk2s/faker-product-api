package com.berk2s.talent.productapi.web.models;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class FormattedCSVRecord {

    private String productName;

    private String productId;

    private Integer listingPrice;

    private Integer salePrice;

    private Integer discount;

    private String brand;

    private String description;

    private Set<String> images = new HashSet<>();


}
