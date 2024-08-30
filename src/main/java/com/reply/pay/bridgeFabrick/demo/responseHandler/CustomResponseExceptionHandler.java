package com.reply.pay.bridgeFabrick.demo.responseHandler;


import com.reply.pay.bridgeFabrick.demo.exception.RestServiceException;
import com.reply.pay.bridgeFabrick.demo.response.upstream.UpstreamErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@Log4j2
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RestServiceException.class)
    ResponseEntity<UpstreamErrorResponse> handleDownstreamException(RestServiceException ex, HttpServletRequest request) {
        log.error("DownStream Response Error [{}] from URI {} | Message: {}",
                ex.getHttpStatus(),
                request.getRequestURI(),
                ex.getMessage());

        UpstreamErrorResponse body = new UpstreamErrorResponse(ex, request.getRequestURI());

        log.info("UpStream Response Error {}", body);

        return new ResponseEntity<>(
                body,
                ex.getHttpStatus());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<UpstreamErrorResponse> handleException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("Exception occurred calling [{}] error message: {}", request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(new UpstreamErrorResponse(ex, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DateTimeParseException.class)
    ResponseEntity<UpstreamErrorResponse> handleException(DateTimeParseException ex, RequestFacade request) {
        log.error("Exception occurred calling [{}] error message: {}", request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(
                new UpstreamErrorResponse(
                        new IllegalArgumentException("Date '" + ex.getParsedString() + "' not valid must have format yyyy-MM-dd"),
                        request.getRequestURI()
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        // we can predispose to respond a list of errors, let's just reply with the first missing or invalid field
        String error = ex.getAllErrors().get(0).getDefaultMessage();

        return new ResponseEntity<>(
                new UpstreamErrorResponse(
                        new IllegalArgumentException(error),
                        request.getRequestURI()
                ),
                HttpStatus.BAD_REQUEST);
    }
}