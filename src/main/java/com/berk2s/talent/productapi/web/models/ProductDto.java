package com.berk2s.talent.productapi.web.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDto {

    private Long id;

    private String productExternalId;

    private String productName;

    private String description;

    private Integer listingPrice;

    private Integer salePrice;

    private Integer discount;

    private Double rating;

    private Set<String> productImages = new HashSet<>();

    private BrandDto brand;

    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}
