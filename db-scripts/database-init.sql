-- MySQL database setup
DROP DATABASE IF EXISTS `pagination-db`;
DROP USER IF EXISTS `pagination-admin`@`%`;

CREATE DATABASE IF NOT EXISTS `pagination-db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `pagination-admin`@`%` IDENTIFIED WITH caching_sha2_password BY 'pass_12%Wq]wOrd';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `pagination-db`.* TO `pagination-admin`@`%`;

USE `pagination-db`;

-- Products Table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE,
    reviews INT,
    recommendations DOUBLE,
    created_at TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
);

-- indexes
CREATE INDEX idx_products_reviews_id
ON products(reviews DESC, id DESC);

CREATE INDEX idx_products_recommendations_id
ON products(recommendations DESC, id DESC);

-- Increase the limit to something higher than 10,00
SET SESSION cte_max_recursion_depth = 1100;

INSERT INTO products (name, price, created_at, updated_at)
WITH RECURSIVE seq AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n + 1 FROM seq WHERE n < 1000
)
SELECT
    CONCAT('Product ', n),
    ROUND(RAND() * 1000, 2),
    -- Subtract (1000 - n) seconds
    -- When n=1, it subtracts 999s (Oldest)
    -- When n=1000, it subtracts 0s (Newest)
    DATE_SUB(NOW(6), INTERVAL (1000 - n) SECOND),
    DATE_SUB(NOW(6), INTERVAL (1000 - n) SECOND)
FROM seq;