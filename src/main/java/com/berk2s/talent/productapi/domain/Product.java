package com.berk2s.talent.productapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Column(name = "product_external_id")
    private String productExternalId;

    @Column(name = "product_name")
    private String productName;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "listing_price")
    private String listingPrice;

    @Column(name = "sale_price")
    private String salePrice;

    @Column(name = "discount")
    private String discount;

    @Column(name = "rating")
    private Double rating;

    @ElementCollection
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "product_images")
    private Set<String> productImages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Brand brand;

    public void addImage(String image) {
        productImages.add(image);
    }

}
