package com.reply.pay.bridgeFabrick.demo.response.upstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reply.pay.bridgeFabrick.demo.exception.RestServiceException;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpstreamErrorResponse {

    private String timestamp;

    /** HTTP Status Code */
    private int status;

    /** HTTP Reason phrase */
    private String error;

    /** A code that describe the error thrown when calling the downstream API */
    private String code;

    /** A message that describe the error thrown when calling the downstream API */
    private String description;

    /** URI that has been called */
    private String uri;

    public UpstreamErrorResponse(RestServiceException ex, String uri) {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.status = ex.getHttpStatus().value();
        this.error = ex.getHttpStatus().getReasonPhrase();
        this.code = ex.getCode();
        this.description = ex.getDescription();
        this.uri = uri;
    }

    public UpstreamErrorResponse(RuntimeException ex, String uri) {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = ex.getMessage();
        this.uri = uri;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(this);
    }
}