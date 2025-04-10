package com.akshit.resources;

import com.akshit.db.entities.UserEntity;
import com.akshit.models.User;
import com.akshit.services.UserService;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "User Related APIs")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserResource {
    private final UserService userService;

    @GET
    @Description("Get all users")
    @Operation(summary = "Get all users")
    @Path("/users")
    @UnitOfWork
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Description("Get user by id")
    @Operation(summary = "Get user by id")
    @Path("/user/{id}")
    @UnitOfWork
    public UserEntity getUser(@PathParam("id") String id) {
        return userService.getUserById(id).orElse(null);
    }

    @POST
    @Description("Create user")
    @Operation(summary = "Create user")
    @Path("/user")
    @UnitOfWork
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @PUT
    @Description("Update user")
    @Operation(summary = "Update user")
    @Path("/user/{id}")
    @UnitOfWork
    public User updateUser(@PathParam("id") String id, User user) {
        return userService.updateUser(id, user);
    }

    @DELETE
    @Description("Delete user")
    @Operation(summary = "Delete user by id")
    @Path("/user/{id}")
    @UnitOfWork
    public User deleteUser(@PathParam("id") String id) {
        return userService.deleteUser(id);
    }
}
