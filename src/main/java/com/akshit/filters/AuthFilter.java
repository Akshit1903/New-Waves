package com.akshit.filters;

import static com.akshit.exceptions.ErrorCode.FILTER_RESOURCE_INFO_METHOD_MISSING;

import com.akshit.annotations.BypassAuth;
import com.akshit.exceptions.NewWavesException;
import com.akshit.services.GcpAccessTokenService;
import com.google.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Objects;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private final GcpAccessTokenService gcpAccessTokenService;
    @Context
    private ResourceInfo resourceInfo;

    @Inject  // Guice will inject this service
    public AuthFilter(GcpAccessTokenService gcpAccessTokenService) {
        this.gcpAccessTokenService = gcpAccessTokenService;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Method method = resourceInfo.getResourceMethod();
        if (Objects.isNull(method)) {
            throw NewWavesException.of(FILTER_RESOURCE_INFO_METHOD_MISSING, null);
        }
        if (method.isAnnotationPresent(BypassAuth.class)) {
            return;
        }
        String accessToken = containerRequestContext.getHeaderString("Authorization");
        gcpAccessTokenService.validateAccessToken(accessToken);
    }
}
