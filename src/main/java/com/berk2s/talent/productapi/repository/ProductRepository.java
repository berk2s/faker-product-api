package com.berk2s.talent.productapi.repository;

import com.berk2s.talent.productapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT COUNT(p) FROM Product p")
    long countAll();

    Page<Product> findAllByProductNameIgnoreCaseStartsWith(String productName, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

}
