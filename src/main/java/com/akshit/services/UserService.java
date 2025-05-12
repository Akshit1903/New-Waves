package com.akshit.services;

import com.akshit.db.dao.UserDAO;
import com.akshit.db.entities.StoredUser;
import com.akshit.exceptions.ErrorCode;
import com.akshit.exceptions.NewWavesException;
import com.akshit.models.User;
import com.akshit.utils.Utils;
import com.google.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserService {

    private final UserDAO userDAO;

    private <T> T executeWithExceptionHandling(Supplier<T> action,
                                               String errorMessage) {
        try {
            return action.get();
        } catch (Exception e) {
            throw NewWavesException.of(ErrorCode.DAO_ERROR, errorMessage + ": " + e.getMessage());
        }
    }

    private StoredUser toUserEntity(User user) {
        return Utils.map(user, StoredUser.class);
    }

    private User toUser(StoredUser storedUser) {
        return Utils.map(storedUser, User.class);
    }

    public User createUser(User user) {
        StoredUser StoredUser = executeWithExceptionHandling(() -> userDAO.createEntity(toUserEntity(user)),
                String.format("Error creating User : %s", user));
        return toUser(StoredUser);
    }

    public Optional<StoredUser> getUserById(String id) {
        return executeWithExceptionHandling(() -> userDAO.findEntityById(id),
                String.format("Error finding User by ID : %s", id));
    }

    public List<User> getAllUsers() {
        return executeWithExceptionHandling(() -> userDAO.findAllEntities()
                .stream()
                .map(this::toUser)
                .toList(), "Error retrieving all Users");
    }

    public User updateUser(String id,
                           User updatedUser) {
        return toUser(executeWithExceptionHandling(() -> userDAO.updateEntity(id, toUserEntity(updatedUser)),
                String.format("Error updating User : %s", id)));
    }

    public User deleteUser(String id) {
        return toUser(executeWithExceptionHandling(() -> userDAO.deleteEntity(id),
                String.format("Error deleting User : %s", id)));
    }

}
