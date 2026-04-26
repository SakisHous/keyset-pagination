package com.app.keyset_pagination.utils;

import com.app.keyset_pagination.dto.ProductResponse;
import com.app.keyset_pagination.model.Product;

import java.time.Instant;
import java.util.List;

public class TestSamples {

    public static List<ProductResponse> getFirstProductPage() {
        return List.of(
            new ProductResponse(11L, "DDR5 RAM 32GB", 149.99, 189, 4.7, Instant.parse("2026-01-01T10:02:00Z"), Instant.parse("2026-01-01T10:04:00Z")),
            new ProductResponse(12L, "CPU Cooling Fan RGB", 59.99, 234, 4.4, Instant.parse("2026-01-01T11:00:00Z"), Instant.parse("2026-01-01T11:00:00Z")),
            new ProductResponse(13L, "Dash Cam 4K Front Rear", 129.99, 219, 3.8, Instant.parse("2026-01-01T13:05:00Z"), Instant.parse("2026-01-01T13:05:00Z")),
            new ProductResponse(14L, "Espresso Machine Semi-Auto", 349.28, 123, 4.1, Instant.parse("2026-01-01T14:00:00Z"), Instant.parse("2026-01-01T14:00:00Z")),
            new ProductResponse(15L, "Wireless Charging Pad", 34.05, 298, 4.3, Instant.parse("2026-01-01T15:00:00Z"), Instant.parse("2026-01-01T15:00:05Z")));
    }

    public static List<ProductResponse> getSecondProductPage() {

        return List.of(
                new ProductResponse(16L, "Travel Adapter Universal", 149.99, 189, 4.7, Instant.parse("2026-01-07T10:02:00Z"), Instant.parse("2026-01-08T10:04:00Z")),
                new ProductResponse(17L, "CPU Cooling Fan RGB", 59.99, 234, 4.4, Instant.parse("2026-01-13T11:00:00Z"), Instant.parse("2026-01-01T14:00:00Z")),
                new ProductResponse(18L, "Dash Cam 4K Front Rear", 129.99, 219, 3.8, Instant.parse("2026-01-14T13:05:00Z"), Instant.parse("2026-02-01T13:05:00Z")),
                new ProductResponse(19L, "Espresso Machine Semi-Auto", 349.28, 123, 4.1, Instant.parse("2026-01-20T14:00:00Z"), Instant.parse("2026-02-01T14:00:00Z")),
                new ProductResponse(20L, "Wireless Charging Pad", 34.05, 298, 4.3, Instant.parse("2026-01-21T15:00:00Z"), Instant.parse("2026-02-01T15:00:05Z"))
        );
    }

    public static List<Product> getFirstProducts() {
        return List.of(
                new Product(11L, "DDR5 RAM 32GB", 149.99, 189, 4.7, Instant.parse("2026-01-01T10:02:00Z"), Instant.parse("2026-01-01T10:04:00Z")),
                new Product(12L, "CPU Cooling Fan RGB", 59.99, 234, 4.4, Instant.parse("2026-01-01T11:00:00Z"), Instant.parse("2026-01-01T11:00:00Z")),
                new Product(13L, "Dash Cam 4K Front Rear", 129.99, 219, 3.8, Instant.parse("2026-01-01T13:05:00Z"), Instant.parse("2026-01-01T13:05:00Z")),
                new Product(14L, "Espresso Machine Semi-Auto", 349.28, 123, 4.1, Instant.parse("2026-01-01T14:00:00Z"), Instant.parse("2026-01-01T14:00:00Z")),
                new Product(15L, "Wireless Charging Pad", 34.05, 298, 4.3, Instant.parse("2026-01-01T15:00:00Z"), Instant.parse("2026-01-01T15:00:05Z")));
    }

    public static List<Product> getSecondProducts() {

        return List.of(
                new Product(16L, "Travel Adapter Universal", 149.99, 189, 4.7, Instant.parse("2026-01-07T10:02:00Z"), Instant.parse("2026-01-08T10:04:00Z")),
                new Product(17L, "CPU Cooling Fan RGB", 59.99, 234, 4.4, Instant.parse("2026-01-13T11:00:00Z"), Instant.parse("2026-01-01T14:00:00Z")),
                new Product(18L, "Dash Cam 4K Front Rear", 129.99, 219, 3.8, Instant.parse("2026-01-14T13:05:00Z"), Instant.parse("2026-02-01T13:05:00Z")),
                new Product(19L, "Espresso Machine Semi-Auto", 349.28, 123, 4.1, Instant.parse("2026-01-20T14:00:00Z"), Instant.parse("2026-02-01T14:00:00Z")),
                new Product(20L, "Wireless Charging Pad", 34.05, 298, 4.3, Instant.parse("2026-01-21T15:00:00Z"), Instant.parse("2026-02-01T15:00:05Z"))
        );
    }
}
