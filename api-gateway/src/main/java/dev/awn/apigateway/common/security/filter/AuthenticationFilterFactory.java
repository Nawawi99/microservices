package dev.awn.apigateway.common.security.filter;

import dev.awn.apigateway.common.exception.BadRequestException;
import dev.awn.apigateway.common.exception.MissingAuthorizationHeaderException;
import dev.awn.apigateway.common.security.utils.JwtUtils;
import dev.awn.apigateway.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.reactive.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<AuthenticationFilterFactory.Config> {
    private final RouteValidator routeValidator;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthenticationFilterFactory(JwtUtils jwtUtils,
                                       RouteValidator routeValidator,
                                       UserService userService) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
        this.routeValidator = routeValidator;
        this.userService = userService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest()
                             .getHeaders()
                             .containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new MissingAuthorizationHeaderException();
                }

                List<String> headers = exchange.getRequest()
                                               .getHeaders()
                                               .get(HttpHeaders.AUTHORIZATION);

                if (headers == null || headers.isEmpty()) {
                    throw new MissingAuthorizationHeaderException();
                }

                String token = exchange.getRequest()
                                       .getHeaders()
                                       .get(HttpHeaders.AUTHORIZATION)
                                       .get(0);

                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                jwtUtils.validateToken(token);

                String username = jwtUtils.extractUserName(token);

                if(!userService.usernameExists(username)) {
                    throw new BadRequestException("Username under that token doesn't match");
                }

                if(!userService.isLatestToken(username, token)) {
                    throw new BadRequestException("Token is obsolete/old");
                }

                List<String> roles = jwtUtils.extractRoles(token);

                if (roles != null && !roles.isEmpty()) {
                    exchange.getRequest()
                            .mutate()
                            .header("X-User-Roles", String.join(",", roles));
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
