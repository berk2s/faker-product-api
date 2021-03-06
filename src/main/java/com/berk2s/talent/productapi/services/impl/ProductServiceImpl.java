package com.berk2s.talent.productapi.services.impl;

import com.berk2s.talent.productapi.domain.Product;
import com.berk2s.talent.productapi.repository.ProductRepository;
import com.berk2s.talent.productapi.services.ProductService;
import com.berk2s.talent.productapi.web.exceptions.ProductNotFoundException;
import com.berk2s.talent.productapi.web.mappers.ProductMapper;
import com.berk2s.talent.productapi.web.models.ErrorDesc;
import com.berk2s.talent.productapi.web.models.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.warn("Product with given id does not exists [productId: {}]", productId);
            throw new ProductNotFoundException(ErrorDesc.PRODUCT_NOT_FOUND.getDesc());
        }

        return productMapper.productToProductDto(product.get());
    }

    @Override
    public Page<ProductDto> getProducts(Pageable pageable, String search) {
        Page<Product> products;

        if(StringUtils.isEmpty(search) || search.trim().equals("")) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository
                    .findAllByProductNameIgnoreCaseStartsWith(search.trim(), pageable);
        }

        log.info("Products have been listed");

        return new PageImpl<>(productMapper.productsToProductsDto(products.getContent()),
                pageable,
                products.getTotalElements());
    }
}
