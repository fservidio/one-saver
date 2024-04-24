package com.starling.onesaver.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Intercepts Validation error in requests such as GET roundup value and save roundup value in saving space
 * when date query param is needed
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
        public String handleValidationExceptions(
                MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    /**
     * Handles errors from the webclient and wraps them in a ResponseEntity
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.class)
    protected ResponseEntity handleWebClientResponseException(WebClientResponseException ex) {

        return new ResponseEntity<>(ex.getResponseBodyAsByteArray(),  ex.getStatusCode());
    }
}
