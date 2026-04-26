package com.app.keyset_pagination.repository;

import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.utils.TestContainersIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest extends TestContainersIntegration {

    @Autowired
    ProductRepository productRepository;

    private static final int PAGE_SIZE = 10;

    @Test
    void shouldFindFirstPageByCreatedAt() {

        List<Product> firstRows = productRepository.findFirstPageByCreatedAt(PAGE_SIZE);

        assertEquals(PAGE_SIZE, firstRows.size());
    }

    @Test
    void shouldFindFirstPageByRecommendations() {

        List<Product> firstRowsForReviews = productRepository.findFirstPageByRecommendations(PAGE_SIZE);

        assertEquals(PAGE_SIZE, firstRowsForReviews.size());
    }

    @Test
    void shouldFindNextProductPageOrderByCreatedDate() {
    }

    @Test
    void shouldFindNextProductPageOrderByRecommendations() {
    }
}