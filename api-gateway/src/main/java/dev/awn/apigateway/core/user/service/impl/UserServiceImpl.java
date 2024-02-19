package dev.awn.apigateway.core.user.service.impl;

import dev.awn.apigateway.common.exception.BadRequestException;
import dev.awn.apigateway.core.user.model.User;
import dev.awn.apigateway.core.user.repository.UserRepository;
import dev.awn.apigateway.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean usernameExists(String username) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username cannot be empty");
        }

        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new BadRequestException("Username doesn't exist"));

        return true;
    }

    @Override
    public boolean isLatestToken(String username, String token) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username cannot be empty");
        }

        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new BadRequestException("Username doesn't exist"));

        if(user.getToken() == null) {
            throw new BadRequestException("Something's gone wrong, try signing in again");
        }

        return user.getToken()
                   .equals(token);
    }
}
