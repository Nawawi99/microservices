package dev.awn.authenticationservice.core.user.repository;

import java.util.Optional;

import dev.awn.authenticationservice.core.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
