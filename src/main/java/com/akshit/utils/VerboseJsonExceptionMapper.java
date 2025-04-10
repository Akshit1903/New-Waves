package com.akshit.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class VerboseJsonExceptionMapper implements ExceptionMapper<JsonMappingException> {
    @Override
    public Response toResponse(JsonMappingException ex) {
        // Return more descriptive error
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "error", "JSON mapping error",
                        "message", ex.getOriginalMessage(),
                        "path", ex.getPathReference()
                ))
                .build();
    }
}
