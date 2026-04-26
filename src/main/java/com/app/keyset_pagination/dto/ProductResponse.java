package com.app.keyset_pagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Setter
@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer reviews;
    private Double recommendations;
    private Instant createdAt;
    private Instant updatedAt;
}
