package com.berk2s.talent.productapi.web.mappers;

import com.berk2s.talent.productapi.domain.Product;
import com.berk2s.talent.productapi.web.models.ProductDto;
import org.mapstruct.*;


import java.util.List;

@Mapper
public interface ProductMapper {

    @Named("ProductConverter")
    @Mappings({
            @Mapping(target = "id", expression = "java( product.getId() )"),
            @Mapping(target = "createdAt", expression = "java( product.getCreatedAt() )"),
            @Mapping(target = "lastModifiedAt", expression = "java( product.getLastModifiedAt() )"),
    })
    ProductDto productToProductDto(Product product);

    @IterableMapping(qualifiedByName = "ProductConverter")
    List<ProductDto> productsToProductsDto(List<Product> products);

}
