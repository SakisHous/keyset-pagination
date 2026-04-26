package com.app.keyset_pagination.service;

import com.app.keyset_pagination.dto.ProductCursor;
import com.app.keyset_pagination.dto.ProductPage;
import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.repository.ProductRepository;
import com.app.keyset_pagination.service.mappers.ProductResponseMapper;
import com.app.keyset_pagination.util.CursorValue;
import com.app.keyset_pagination.util.ProductSortField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductPage getProductsPage(String token, ProductSortField sortField, int size) {

        List<Product> rows = (token == null) ? getFirstPage(sortField, size) :
                getNextPage(ProductCursor.decode(token), size);


        String nextCursor = rows.isEmpty() ? null
                : ProductCursor.from(rows.getLast(), sortField).encode();

        return ProductPage.builder()
                .data(productResponseMapper.toProductResponseList(rows))
                .nextCursor(nextCursor)
                .hasNext(nextCursor != null)
                .build();
    }

    private List<Product> getFirstPage(ProductSortField sortField, int size) {

        return switch(sortField) {
            case CREATED_AT -> productRepository.findFirstPageByCreatedAt(size);
            case RECOMMENDATIONS -> productRepository.findFirstPageByRecommendations(size);
        };
    }

    private List<Product> getNextPage(ProductCursor cursor, int size) {

        return switch (cursor.cursor()) {
            case CursorValue.OfInstant(Instant v) -> productRepository.findNextProductPageOrderByCreatedDate(v, cursor.id(), size);
            case CursorValue.OfDouble(Double v) -> productRepository.findNextProductPageOrderByRecommendations(v, cursor.id(), size);
        };
    }
}
