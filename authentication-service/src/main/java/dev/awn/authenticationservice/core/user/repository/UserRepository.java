package dev.awn.authenticationservice.core.user.repository;

import java.util.Optional;

import dev.awn.authenticationservice.core.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
