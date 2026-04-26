package com.app.keyset_pagination.controller;

import com.app.keyset_pagination.dto.ProductCursor;
import com.app.keyset_pagination.dto.ProductPage;
import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.repository.ProductRepository;
import com.app.keyset_pagination.service.ProductService;
import com.app.keyset_pagination.util.ProductSortField;
import com.app.keyset_pagination.utils.TestSamples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Base64;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductRepository productRepository;

    private static final String BASE_URL = "/api/v1/products";

    @Test
    void shouldReturnFirstProductsPageWhenCursorProvided() throws Exception {

        when(productService.getProductsPage(null, ProductSortField.CREATED_AT, 5))
                .thenReturn(mockFirstPage("next-cursor-token", true));

        mockMvc.perform(get(BASE_URL)
                        .param("sortKey", String.valueOf(ProductSortField.CREATED_AT))
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.nextCursor").value("next-cursor-token"))
                .andExpect(jsonPath("$.hasNext").value(true));
    }

    @Test
    void shouldGetProductPageWithCursorProvided() throws Exception {

        Product lastProduct = new Product(15L, "Wireless Charging Pad", 34.05, 298, 4.3, Instant.parse("2026-01-01T15:00:00Z"), Instant.parse("2026-01-01T15:00:05Z"));
        ProductSortField sortField = ProductSortField.CREATED_AT;

        String cursor = ProductCursor.from(lastProduct, sortField).encode();

        when(productService.getProductsPage(cursor, ProductSortField.CREATED_AT, 5))
                .thenReturn(mockFirstPage(null, false));

        mockMvc.perform(get(BASE_URL)
                        .param("cursor", cursor)
                        .param("sortKey", String.valueOf(ProductSortField.CREATED_AT))
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.nextCursor").doesNotExist())
                .andExpect(jsonPath("$.hasNext").value(false));
    }

    @Test
    void invalidProductSortField() throws Exception {
        mockMvc.perform(get(BASE_URL).param("sort", "INVALID_FIELD"))
                .andExpect(status().isBadRequest());
    }

    private String encodeCursor(String value) {
        return Base64.getUrlEncoder().encodeToString(value.getBytes());
    }

    private ProductPage mockFirstPage(String cursor, boolean hasNext) {
        return new ProductPage(
                TestSamples.getFirstProductPage(),
                cursor,
                hasNext
        );
    }

    private ProductPage mockSecondPage(String cursor, boolean hasNext) {
        return new ProductPage(
                TestSamples.getSecondProductPage(),
                cursor,
                hasNext
        );
    }
}