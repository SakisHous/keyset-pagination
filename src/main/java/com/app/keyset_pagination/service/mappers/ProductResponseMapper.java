package com.app.keyset_pagination.service.mappers;

import com.app.keyset_pagination.dto.ProductResponse;
import com.app.keyset_pagination.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);
}
