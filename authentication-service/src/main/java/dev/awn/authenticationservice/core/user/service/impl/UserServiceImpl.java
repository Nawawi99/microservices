package dev.awn.authenticationservice.core.user.service.impl;

import dev.awn.authenticationservice.common.exception.BadRequestException;
import dev.awn.authenticationservice.common.security.utils.JwtUtils;
import dev.awn.authenticationservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.authenticationservice.core.user.dto.SignInRequest;
import dev.awn.authenticationservice.core.user.dto.SignUpRequest;
import dev.awn.authenticationservice.core.user.dto.UserDTO;
import dev.awn.authenticationservice.core.user.mapper.UserMapper;
import dev.awn.authenticationservice.core.user.model.User;
import dev.awn.authenticationservice.core.user.repository.UserRepository;
import dev.awn.authenticationservice.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           UserRepository userRepository,
                           JwtUtils jwtUtils,
                           @Lazy AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthenticationResponse signup(SignUpRequest request) {
        String email = request.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(email + " is already used");
        }

        User user = userMapper.toEntity(request);

        User createdUser = userRepository.save(user);

        String jwt = jwtUtils.generateToken(createdUser);

        UserDTO userDTO = userMapper.toDTO(user);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> new BadRequestException("Email doesn't exist"));

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (request.getEmail(), request.getPassword()));

        if(!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        String jwt = jwtUtils.generateToken(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }

}
