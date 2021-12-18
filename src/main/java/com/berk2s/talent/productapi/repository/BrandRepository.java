package com.berk2s.talent.productapi.repository;

import com.berk2s.talent.productapi.domain.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Long> {
    boolean existsByBrandName(String brandName);

    Optional<Brand> findByBrandName(String brandName);
}
