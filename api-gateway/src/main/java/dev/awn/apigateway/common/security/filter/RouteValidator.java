package dev.awn.apigateway.common.security.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> permittedEndpoints = List.of(
            "/api/v1/users/signup",
            "/api/v1/users/signin",
            "/eureka/web"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> permittedEndpoints.stream()
                                                   .noneMatch(uri -> serverHttpRequest.getURI()
                                                                                      .getPath()
                                                                                      .contains(uri));

}
