package com.app.keyset_pagination.util;

import com.app.keyset_pagination.model.Product;

import java.time.Instant;

public enum ProductSortField {

    CREATED_AT {
        @Override
        public String extractRaw(Product p) {
            return p.getCreatedAt().toString();
        }
        @Override
        public CursorValue parse(String raw) {
            return new CursorValue.OfInstant(Instant.parse(raw));
        }
    },
    RECOMMENDATIONS {
        @Override
        public String extractRaw(Product p) {
            return p.getRecommendations().toString();
        }
        @Override
        public CursorValue parse(String raw) {
            return new CursorValue.OfDouble(Double.parseDouble(raw));
        }
    };

    public abstract String extractRaw(Product p);
    public abstract CursorValue parse(String raw);
}
