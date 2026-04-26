package com.app.keyset_pagination.repository;

import com.app.keyset_pagination.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        SELECT * FROM products
        ORDER BY created_at DESC, id DESC
        LIMIT :size
    """, nativeQuery = true)
    List<Product> findFirstPageByCreatedAt(@Param("size") int size);

    @Query(value = """
        SELECT * FROM products
        ORDER BY recommendations DESC, id DESC
        LIMIT :size
    """, nativeQuery = true)
    List<Product> findFirstPageByRecommendations(@Param("size") int size);

    @Query(value = """
        SELECT * FROM products
        WHERE (created_at, id) < (:lastCreatedAt, :lastId)
        ORDER BY created_at DESC, id DESC
        LIMIT :size
    """, nativeQuery = true)
    List<Product> findNextProductPageOrderByCreatedDate(
            @Param("lastCreatedAt") Instant lastCreatedAt,
            @Param("lastId") Long lastId,
            @Param("size") int size
    );

    @Query(value = """
        SELECT * FROM products
        WHERE (recommendations, id) < (:lastRecommendations, :lastId)
        ORDER BY recommendations DESC, id DESC
        LIMIT :size
    """, nativeQuery = true)
    List<Product> findNextProductPageOrderByRecommendations(
            @Param("lastRecommendations") Double lastRecommendations,
            @Param("lastId") Long lastId,
            @Param("size") int size
    );
}
