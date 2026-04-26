package com.app.keyset_pagination.controller;

import com.app.keyset_pagination.dto.ProductPage;
import com.app.keyset_pagination.util.ProductSortField;
import com.app.keyset_pagination.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Tag(name = "Products", description = "Operations for managing products")
    @Operation(
            summary = "Get paginated list of products",
            description = "Returns a cursor-based paginated list of products. Max page size is 100."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductPage.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ProductPage> getProducts(
            @Parameter(description = "Cursor for pagination (base64 encoded)", example = "eyJpZCI6IjEyMyJ9")
            @RequestParam(required = false) String cursor,
            @Parameter(description = "Field to sort products by", example = "created_at")
            @RequestParam(defaultValue = "created_at") ProductSortField sortKey,
            @Parameter(description = "Number of products per page (max 100)", example = "20")
            @RequestParam(defaultValue = "size") int size) {

        size = Math.min(size, 100);

        return ResponseEntity.ok(productService.getProductsPage(cursor, sortKey, size));
    }
}
