package dev.awn.authenticationservice.core.user.service;

import dev.awn.authenticationservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.authenticationservice.core.user.dto.SignInRequest;
import dev.awn.authenticationservice.core.user.dto.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);

}
