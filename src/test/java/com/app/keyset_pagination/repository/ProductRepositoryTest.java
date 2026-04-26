package com.app.keyset_pagination.repository;

import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.utils.TestContainersIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<Product> firstRowsForRecommendations = productRepository.findFirstPageByRecommendations(PAGE_SIZE);

        assertEquals(PAGE_SIZE, firstRowsForRecommendations.size());
    }

    @Test
    void shouldFindNextProductPageOrderByCreatedDate() {

        List<Product> nextPage = productRepository.findNextProductPageOrderByCreatedDate(Instant.now(), 10L, PAGE_SIZE);

        assertFalse(nextPage.isEmpty());
        assertEquals(PAGE_SIZE, nextPage.size());
    }

    @Test
    void shouldFindNextProductPageOrderByRecommendations() {

        final double lastRecommendations = 4.5;
        final long lastId = 10L;

        List<Product> nextPage = productRepository.findNextProductPageOrderByRecommendations(lastRecommendations, lastId, PAGE_SIZE);

        assertFalse(nextPage.isEmpty());
        assertEquals(PAGE_SIZE, nextPage.size());
    }
}