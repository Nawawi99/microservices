package dev.awn.authenticationservice.core.user.mapper;

import dev.awn.authenticationservice.core.user.dto.UserDTO;
import dev.awn.authenticationservice.core.user.dto.SignUpRequest;
import dev.awn.authenticationservice.core.user.model.Role;
import dev.awn.authenticationservice.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(SignUpRequest signUpRequest) {
        return User.builder()
                   .firstName(signUpRequest.getFirstName())
                   .lastName(signUpRequest.getLastName())
                   .username(signUpRequest.getUsername())
                   .password(passwordEncoder.encode(signUpRequest.getPassword()))
                   .roles(List.of(Role.ROLE_USER))
                   .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                      .id(user.getId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .username(user.getUsername())
                      .roles(user.getRoles())
                      .build();
    }
}
