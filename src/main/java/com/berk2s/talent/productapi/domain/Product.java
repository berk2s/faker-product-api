package com.berk2s.talent.productapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Column(name = "product_external_id", unique = true)
    private String productExternalId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "listing_price")
    private Integer listingPrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "discount")
    private Integer discount;

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
