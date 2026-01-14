package com.campus.complaintmanagement.exception;

import com.campus.complaintmanagement.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class GlobalHandlerException {
    @RestControllerAdvice

    public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFound.class)
        public ResponseEntity<ApiError> handleNotFound(
                ResourceNotFound ex,
                HttpServletRequest request) {

            ApiError error = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.name(),
                    ex.getMessage(),
                    request.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ApiError> handleUnauthorized(
                UnauthorizedException ex,
                HttpServletRequest request) {

            ApiError error = new ApiError(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.name(),
                    ex.getMessage(),
                    request.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGeneric(
                Exception ex,
                HttpServletRequest request) {

            ApiError error = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.name(),
                    ex.getMessage(),
                    request.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
