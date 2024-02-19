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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           UserRepository userRepository,
                           JwtUtils jwtUtils,
                           @Lazy AuthenticationManager authenticationManager,
                           MongoTemplate mongoTemplate) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.mongoTemplate = mongoTemplate;
    }


    public JwtAuthenticationResponse signup(SignUpRequest request) {
        String username = request.getUsername();

        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException(username + " is already used");
        }

        User user = userMapper.toEntity(request);

        String jwt = jwtUtils.generateToken(user);

        user.setToken(jwt);

        User createdUser = userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(createdUser);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new UsernameNotFoundException("Username doesn't exist"));

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword()));

        if(!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        String jwt = jwtUtils.generateToken(user);

        // Update the token of that user
        Query query = new Query(Criteria.where("username").is(user.getUsername()));
        Update update = new Update().set("token", jwt);
        mongoTemplate.updateFirst(query, update, User.class);

        UserDTO userDTO = userMapper.toDTO(user);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }

}
