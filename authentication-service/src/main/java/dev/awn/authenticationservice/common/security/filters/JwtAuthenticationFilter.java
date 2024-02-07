package dev.awn.authenticationservice.common.security.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.awn.authenticationservice.common.security.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

public class JwtAuthenticationFilter {}
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//  private final JwtUtils jwtUtils;
//  private final UserDetailsService userDetailsService;
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request,
//        HttpServletResponse response,
//        FilterChain filterChain)
//        throws ServletException, IOException {
//      final String authHeader = request.getHeader("Authorization");
//      final String jwt;
//      final String userEmail;
//      if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
//          filterChain.doFilter(request, response);
//          return;
//      }
//      jwt = authHeader.substring(7);
//      log.debug("JWT - {}", jwt.toString());
//      userEmail = jwtUtils.extractUserName(jwt);
//      if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
//          UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//
//          if (jwtUtils.isTokenValid(jwt, userDetails)) {
//            log.debug("User - {}", userDetails);
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            context.setAuthentication(authToken);
//            SecurityContextHolder.setContext(context);
//          }
//      }
//      filterChain.doFilter(request, response);
//  }
//}