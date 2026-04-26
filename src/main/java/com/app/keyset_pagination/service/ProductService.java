package com.app.keyset_pagination.service;

import com.app.keyset_pagination.dto.ProductPage;
import com.app.keyset_pagination.util.ProductSortField;

public interface ProductService {

   ProductPage getProductsPage(String cursor, ProductSortField sortKey, int size);
}
