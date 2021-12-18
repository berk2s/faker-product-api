package com.berk2s.talent.productapi.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("Getting Products")
    @Nested
    class GettingProducts {

        @DisplayName("Get Products By Range")
        @Test
        void getProductsByRange() throws Exception {

            mockMvc.perform(get(ProductController.ENDPOINT + "?page=0&size=12"))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(12)))
                    .andExpect(jsonPath("$.content..id").isNotEmpty())
                    .andExpect(jsonPath("$.content..productExternalId").isNotEmpty())
                    .andExpect(jsonPath("$.content..productName").isNotEmpty())
                    .andExpect(jsonPath("$.content..description").isNotEmpty())
                    .andExpect(jsonPath("$.content..listingPrice").isNotEmpty())
                    .andExpect(jsonPath("$.content..salePrice").isNotEmpty())
                    .andExpect(jsonPath("$.content..discount").isNotEmpty())
                    .andExpect(jsonPath("$.content..rating").isNotEmpty())
                    .andExpect(jsonPath("$.content..productImages").isNotEmpty())
                    .andExpect(jsonPath("$.content..brand..brandName").isNotEmpty());

        }

        @DisplayName("Get Products By Search Key")
        @Test
        void getProductsBySearchKey() throws Exception {

            mockMvc.perform(get(ProductController.ENDPOINT + "?page=0&size=12&search=Men"))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()", is(12)))
                    .andExpect(jsonPath("$.content..id").isNotEmpty())
                    .andExpect(jsonPath("$.content..productExternalId").isNotEmpty())
                    .andExpect(jsonPath("$.content..productName").isNotEmpty())
                    .andExpect(jsonPath("$.content..description").isNotEmpty())
                    .andExpect(jsonPath("$.content..listingPrice").isNotEmpty())
                    .andExpect(jsonPath("$.content..salePrice").isNotEmpty())
                    .andExpect(jsonPath("$.content..discount").isNotEmpty())
                    .andExpect(jsonPath("$.content..rating").isNotEmpty())
                    .andExpect(jsonPath("$.content..productImages").isNotEmpty())
                    .andExpect(jsonPath("$.content..brand..brandName").isNotEmpty());

        }

    }

}
