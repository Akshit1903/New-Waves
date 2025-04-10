package com.akshit.utils.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NewWavesExceptionMapper implements ExceptionMapper<NewWavesException> {
    @Override
    public Response toResponse(NewWavesException exception) {
        return Response.status(exception.getStatus())
                .entity(exception)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

