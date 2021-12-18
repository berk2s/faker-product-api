package com.berk2s.talent.productapi.web.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BrandDto {

    private Long id;

    private String brandName;

    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}
