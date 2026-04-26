package com.app.keyset_pagination.service;

import com.app.keyset_pagination.dto.ProductCursor;
import com.app.keyset_pagination.dto.ProductPage;
import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.repository.ProductRepository;
import com.app.keyset_pagination.service.mappers.ProductResponseMapper;
import com.app.keyset_pagination.service.mappers.ProductResponseMapperImpl;
import com.app.keyset_pagination.util.CursorValue;
import com.app.keyset_pagination.util.ProductSortField;
import com.app.keyset_pagination.utils.TestSamples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Spy
    ProductResponseMapper productResponseMapper = new ProductResponseMapperImpl();

    private static final int PAGE_SIZE = 5;

    @Test
    void shouldReturnFirstProductsPageWithCreatedAt() {

        when(productRepository.findFirstPageByCreatedAt(PAGE_SIZE)).thenReturn(TestSamples.getFirstProducts());

        ProductPage productPage = productService.getProductsPage(null, ProductSortField.CREATED_AT, PAGE_SIZE);

        assertThat(productPage.getData().size()).isEqualTo(5);
        verify(productResponseMapper).toProductResponseList(anyList());
    }

    @Test
    void shouldReturnNextPageGivenCursorCreatedAt() {

        Product lastProduct = TestSamples.getFirstProducts().getLast();
        long lastId = lastProduct.getId();
        Instant lastCreatedAt = lastProduct.getCreatedAt();

        String cursor = new ProductCursor(ProductSortField.CREATED_AT,
                                          new CursorValue.OfInstant(lastCreatedAt),
                                          lastId)
                                    .encode();

        when(productRepository.findNextProductPageOrderByCreatedDate(lastProduct.getCreatedAt(), lastProduct.getId(), PAGE_SIZE))
                .thenReturn(TestSamples.getSecondProducts());

        ProductPage page = productService.getProductsPage(cursor, ProductSortField.CREATED_AT, PAGE_SIZE);

        assertThat(page.getData().size()).isEqualTo(PAGE_SIZE);
        verify(productRepository).findNextProductPageOrderByCreatedDate(lastCreatedAt, lastId, PAGE_SIZE);
    }

    @Test
    void shouldReturnNextPageGivenCursorRecommendations() {

        Product lastProduct = TestSamples.getFirstProducts().getLast();
        long lastId = lastProduct.getId();
        Instant lastCreatedAt = lastProduct.getCreatedAt();

        String cursor = new ProductCursor(ProductSortField.CREATED_AT,
                new CursorValue.OfInstant(lastCreatedAt),
                lastId)
                .encode();

        when(productRepository.findNextProductPageOrderByCreatedDate(lastProduct.getCreatedAt(), lastProduct.getId(), PAGE_SIZE))
                .thenReturn(TestSamples.getSecondProducts());

        ProductPage page = productService.getProductsPage(cursor, ProductSortField.CREATED_AT, PAGE_SIZE);

        assertThat(page.getData().size()).isEqualTo(5);
        verify(productRepository).findNextProductPageOrderByCreatedDate(lastCreatedAt, lastId, PAGE_SIZE);
    }
}