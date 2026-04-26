package com.app.keyset_pagination.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductPage {

    List<ProductResponse> data;
    String nextCursor;
    Boolean hasNext;
}
