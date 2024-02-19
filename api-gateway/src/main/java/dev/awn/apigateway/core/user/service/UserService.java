package dev.awn.apigateway.core.user.service;

public interface UserService {
    boolean usernameExists(String username);

    boolean isLatestToken(String username, String token);
}
