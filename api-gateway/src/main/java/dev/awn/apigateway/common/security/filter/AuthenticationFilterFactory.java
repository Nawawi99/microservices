package dev.awn.apigateway.common.security.filter;

import dev.awn.apigateway.common.exception.MissingAuthorizationHeaderException;
import dev.awn.apigateway.common.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<AuthenticationFilterFactory.Config> {
    private final RouteValidator routeValidator;
    private final JwtUtils jwtUtils;

    public AuthenticationFilterFactory(JwtUtils jwtUtils,
                                       RouteValidator routeValidator) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
        this.routeValidator = routeValidator;
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

                if (headers == null) {
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
