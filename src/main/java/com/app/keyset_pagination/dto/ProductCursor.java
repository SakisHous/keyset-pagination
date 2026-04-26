package com.app.keyset_pagination.dto;

import com.app.keyset_pagination.model.Product;
import com.app.keyset_pagination.service.exceptions.InvalidCursorException;
import com.app.keyset_pagination.util.CursorValue;
import com.app.keyset_pagination.util.ProductSortField;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public record ProductCursor(ProductSortField sortField, CursorValue cursor, long id) {

    public static final String INVALID_TOKEN_PROBLEM_TITLE =  "Token not valid Base64";
    public static final String INVALID_NUMBER_DELIMITED_PROBLEM_TITLE =  "Expected 3 pipe-delimited segments, got %d";

    public String encode() {

        String rawToken = sortField.name() + "|" + sortField.extractRaw(toProduct()) + "|" + id;

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
    }

    public static ProductCursor decode(String token) {

        byte[] bytes;
        try {
            bytes = Base64.getUrlDecoder().decode(token);
        } catch (IllegalArgumentException e) {
            throw new InvalidCursorException(INVALID_TOKEN_PROBLEM_TITLE, e);
        }

        String raw = new String(bytes, StandardCharsets.UTF_8);
        String[] parts = raw.split("\\|", 3);

        if (parts.length != 3) {
            throw new InvalidCursorException(INVALID_NUMBER_DELIMITED_PROBLEM_TITLE.formatted(parts.length));
        }

        ProductSortField sortField = ProductSortField.valueOf(parts[0]);
        CursorValue sortValue = sortField.parse(parts[1]);
        long id = Long.parseLong(parts[2]);

        return new ProductCursor(sortField, sortValue, id);
    }

    private Product toProduct() {
        Product p = new Product();
        p.setId(id);
        switch (cursor) {
            case CursorValue.OfInstant(Instant v) -> p.setCreatedAt(v);
            case CursorValue.OfDouble (Double  v) -> {
                if (sortField == ProductSortField.RECOMMENDATIONS) p.setRecommendations(v);
            }
        }
        return p;
    }

    public static ProductCursor from(Product p, ProductSortField field) {
        CursorValue value = switch (field) {
            case CREATED_AT      -> new CursorValue.OfInstant(p.getCreatedAt());
            case RECOMMENDATIONS -> new CursorValue.OfDouble(p.getRecommendations());
        };
        return new ProductCursor(field, value, p.getId());
    }
}
