package com.berk2s.talent.productapi.repository;

import com.berk2s.talent.productapi.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT COUNT(p) FROM Product p")
    long countAll();
}
