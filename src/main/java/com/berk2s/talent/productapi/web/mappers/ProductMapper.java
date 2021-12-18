package com.berk2s.talent.productapi.web.mappers;

import com.berk2s.talent.productapi.domain.Product;
import com.berk2s.talent.productapi.web.models.ProductDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


import java.util.List;

@Mapper
public interface ProductMapper {

    @Named("ProductConverter")
    ProductDto productToProductDto(Product product);

    @IterableMapping(qualifiedByName = "ProductConverter")
    List<ProductDto> productsToProductsDto(List<Product> products);

}
