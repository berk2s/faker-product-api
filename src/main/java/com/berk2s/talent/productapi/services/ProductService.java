package com.berk2s.talent.productapi.services;

import com.berk2s.talent.productapi.web.models.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto getProductById(Long productId);

    Page<ProductDto> getProducts(Pageable pageable, String search);

}
