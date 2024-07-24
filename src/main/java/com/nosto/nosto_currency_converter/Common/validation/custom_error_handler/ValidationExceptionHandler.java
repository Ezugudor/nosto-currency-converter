package com.nosto.nosto_currency_converter.Common.validation.custom_error_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e,
                        WebRequest request) {
                List<ValidationErrorResponse.FieldError> errors = e.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(error -> new ValidationErrorResponse.FieldError(
                                                ((FieldError) error).getField(),
                                                error.getDefaultMessage()))
                                .collect(Collectors.toList());

                ValidationErrorResponse errorResponse = new ValidationErrorResponse();
                errorResponse.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
                errorResponse.setMessage("Validation failed");
                errorResponse.setErrors(errors);
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException e,
                        WebRequest request) {
                List<ValidationErrorResponse.FieldError> errors = e.getConstraintViolations()
                                .stream()
                                .map(violation -> new ValidationErrorResponse.FieldError(
                                                violation.getPropertyPath().toString(),
                                                violation.getMessage()))
                                .collect(Collectors.toList());

                ValidationErrorResponse errorResponse = new ValidationErrorResponse();
                errorResponse.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
                errorResponse.setMessage("Validation failed");
                errorResponse.setErrors(errors);
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

}