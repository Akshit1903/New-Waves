package com.akshit.resources;

import com.akshit.annotations.BypassAuth;
import com.akshit.services.GcpAccessTokenService;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.naming.AuthenticationException;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;

@Path("/housekeeping")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Housekeeping APIs", description = "Housekeeping APIs")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HousekeepingResource {

    GcpAccessTokenService gcpAccessTokenService = new GcpAccessTokenService();

    @GET
    @BypassAuth
    @Description("Example API")
    @Operation(summary = "No action")
    @Path("/abc")
    @UnitOfWork
    public Response getUser() {
        return Response.ok()
                .build();
    }

    @GET
    @Description("Example API")
    @Operation(summary = "No action")
    @Path("/auth/{token}")
    @UnitOfWork
    public Tokeninfo verify(@PathParam("token") String token)
            throws AuthenticationException, GeneralSecurityException, IOException {
        return gcpAccessTokenService.validateAccessToken(token);
    }

}
