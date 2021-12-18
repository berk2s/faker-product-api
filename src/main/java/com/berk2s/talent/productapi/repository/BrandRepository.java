package com.berk2s.talent.productapi.repository;

import com.berk2s.talent.productapi.domain.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Long> {
}
