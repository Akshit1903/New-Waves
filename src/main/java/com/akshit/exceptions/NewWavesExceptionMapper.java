package com.akshit.exceptions;

import com.akshit.utils.Utils;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NewWavesExceptionMapper implements ExceptionMapper<NewWavesException> {

    @Override
    public Response toResponse(NewWavesException exception) {
        return Response.status(exception.getStatus())
                .entity(Utils.map(exception, NewWavesExceptionResponse.class))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

