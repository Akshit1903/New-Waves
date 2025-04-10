package com.akshit.services;

import com.akshit.db.dao.UserDAO;
import com.akshit.db.entities.UserEntity;
import com.akshit.models.User;
import com.akshit.utils.Utils;
import com.akshit.utils.exceptions.NewWavesException;
import com.google.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserService {
    private final UserDAO userDAO;

    private <T> T executeWithExceptionHandling(Supplier<T> action, String errorMessage) {
        try {
            return action.get();
        } catch (Exception e) {
            throw new NewWavesException(Response.Status.INTERNAL_SERVER_ERROR, errorMessage + ": " + e.getMessage());
        }
    }

    private UserEntity toUserEntity(User user) {
        return Utils.map(user, UserEntity.class);
    }

    private User toUser(UserEntity userEntity) {
        return Utils.map(userEntity, User.class);
    }

    public User createUser(User user) {
        UserEntity UserEntity = executeWithExceptionHandling(() -> userDAO.createEntity(toUserEntity(user)), String.format("Error creating User : %s", user));
        return toUser(UserEntity);
    }

    public Optional<UserEntity> getUserById(String id) {
        return executeWithExceptionHandling(() -> userDAO.findEntityById(id), String.format("Error finding User by ID : %s", id));
    }

    public List<User> getAllUsers() {
        return executeWithExceptionHandling(() -> userDAO.findAllEntities().stream().map(this::toUser).toList(), "Error retrieving all Users");
    }

    public User updateUser(String id, User updatedUser) {
        return toUser(executeWithExceptionHandling(() -> userDAO.updateEntity(id, toUserEntity(updatedUser)), String.format("Error updating User : %s", id)));
    }

    public User deleteUser(String id) {
        return toUser(executeWithExceptionHandling(() -> userDAO.deleteEntity(id), String.format("Error deleting User : %s", id)));
    }

}
