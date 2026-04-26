package com.app.keyset_pagination.dto;

import com.app.keyset_pagination.service.exceptions.InvalidCursorException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.app.keyset_pagination.dto.ProductCursor.INVALID_NUMBER_DELIMITED_PROBLEM_TITLE;
import static com.app.keyset_pagination.dto.ProductCursor.INVALID_TOKEN_PROBLEM_TITLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductCursorTest {

    @Test
    void shouldThrowInvalidCursorExceptionWhenDecodeInvalidBase64() {

        assertThatThrownBy(() -> ProductCursor.decode("not-valid-base64!!!"))
                .isInstanceOf(InvalidCursorException.class)
                .hasMessageContaining(INVALID_TOKEN_PROBLEM_TITLE);
    }

    @Test
    void shouldThrowInvalidCursorExceptionWhenMissingSegments() {

        String delimited = "CREATED_AT|2024-06-01T10:00:00Z";
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(delimited.getBytes(StandardCharsets.UTF_8));

        assertThatThrownBy(() -> ProductCursor.decode(token))
                .isInstanceOf(InvalidCursorException.class)
                .hasMessageContaining(INVALID_NUMBER_DELIMITED_PROBLEM_TITLE.formatted(2));
    }
}