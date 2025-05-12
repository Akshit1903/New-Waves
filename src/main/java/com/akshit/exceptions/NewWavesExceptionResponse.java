package com.akshit.exceptions;

import jakarta.ws.rs.core.Response;
import lombok.Data;

@Data
public class NewWavesExceptionResponse {

    private String summary;
    private String description;
    private Response.Status status;
    private ErrorCode errorCode;
}
