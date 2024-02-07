package dev.awn.productservice.common.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<String> roles = extractRolesFromHeaders(request);

        if (!roles.isEmpty()) {
            List<GrantedAuthority> authorities = roles.stream()
                                                      .map(SimpleGrantedAuthority::new)
                                                      .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken("", "", authorities);

            // Set the Authentication object in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("From Filter" + authentication.getAuthorities());
        }

        filterChain.doFilter(request, response);
    }

    private List<String> extractRolesFromHeaders(HttpServletRequest request) {
        String rolesHeader = request.getHeader("X-User-Roles");

        if (StringUtils.hasText(rolesHeader)) {
            return Arrays.asList(rolesHeader.split(","));
        }

        return List.of(); // or return null if you prefer
    }
}

