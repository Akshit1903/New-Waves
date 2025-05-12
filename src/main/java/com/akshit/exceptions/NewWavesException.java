package com.akshit.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from response
public class NewWavesException extends RuntimeException {

    private final String summary;
    private final String description;
    private final Response.Status status;
    private final ErrorCode errorCode;


    public NewWavesException(String message,
                             String summary,
                             String description,
                             Response.Status status,
                             ErrorCode errorCode) {
        super(message);
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.errorCode = errorCode;
    }

    public static NewWavesException of(ErrorCode errorCode,
                                       String message) {
        return new NewWavesException(message, errorCode.getSummary(), errorCode.getDescription(), errorCode.getCode(),
                errorCode);
    }
}