package dev.awn.authenticationservice.common.handler;


import dev.awn.authenticationservice.common.exception.BadRequestException;
import dev.awn.authenticationservice.common.exception.InternalServerErrorException;
import dev.awn.authenticationservice.common.exception.NotFoundException;
import dev.awn.authenticationservice.common.exception.ValidationException;
import dev.awn.authenticationservice.common.handler.response.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        String errorMessage = "Validation errors occurred: " + ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                                        errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException
            (NotFoundException ex) {
        String errorMessage = "Data wasn't found: " + ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.NOT_FOUND.value(),
                                                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                                                        errorMessage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException
            (InternalServerErrorException ex) {
        String errorMessage = "The service faced an issue, try again later: " + ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus
                                                                .INTERNAL_SERVER_ERROR
                                                                .value(),
                                                        HttpStatus
                                                                .INTERNAL_SERVER_ERROR
                                                                .getReasonPhrase(),
                                                        errorMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                                        ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                                        "Required body is missing");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                                                        HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                                                        "Service is currently unavailable, try again later");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        HttpStatus.UNAUTHORIZED.value(),
                                                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                                                        ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}