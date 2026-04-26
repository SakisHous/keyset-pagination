package com.app.keyset_pagination.controller;

import com.app.keyset_pagination.service.exceptions.InvalidCursorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidCursorException.class)
    public ResponseEntity<ProblemDetail> handleInvalidCursor(InvalidCursorException ex, String msg) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        problem.setTitle(msg);

        return ResponseEntity.badRequest().body(problem);
    }
}
