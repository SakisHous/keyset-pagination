package com.app.keyset_pagination.utils;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@Sql(
        scripts = {"/sql/schema.sql", "/sql/test-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class TestContainersIntegration {

    private static final DockerImageName MYSQL_IMAGE = DockerImageName.parse("mysql:8.3");

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(MYSQL_IMAGE)
            .withDatabaseName("pagination-db")
            .withUsername("pagination-admin")
            .withPassword("pagination-pass");

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }
}
