package com.berk2s.talent.productapi.web.controllers;

import com.berk2s.talent.productapi.services.ProductService;
import com.berk2s.talent.productapi.utils.SortingUtils;
import com.berk2s.talent.productapi.web.models.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Controller", description = "Exposes product endpoints")
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(ProductController.ENDPOINT)
@RestController
public class ProductController {

    public static final String ENDPOINT = "/products";

    private final ProductService productService;

    @Operation(summary = "Get Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products are listed"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "12") Integer size,
                                                        @RequestParam(defaultValue = "createdAt") String sort,
                                                        @RequestParam(defaultValue = "asc") String order,
                                                        @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(productService.getProducts(PageRequest.of(page, size, SortingUtils.generateSort(sort, order)), search), HttpStatus.OK);
    }

}
