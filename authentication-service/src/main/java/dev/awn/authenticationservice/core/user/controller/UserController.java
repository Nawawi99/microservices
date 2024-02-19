package dev.awn.authenticationservice.core.user.controller;

import dev.awn.authenticationservice.common.exception.ValidationException;
import dev.awn.authenticationservice.core.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import dev.awn.authenticationservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.authenticationservice.core.user.dto.SignInRequest;
import dev.awn.authenticationservice.core.user.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@Validated @RequestBody SignUpRequest request,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        JwtAuthenticationResponse jwtAuthenticationResponse = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(jwtAuthenticationResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Validated @RequestBody SignInRequest request,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        JwtAuthenticationResponse jwtAuthenticationResponse = userService.signin(request);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(jwtAuthenticationResponse);
    }

}
