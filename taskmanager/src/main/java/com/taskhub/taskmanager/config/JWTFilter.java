package com.taskhub.taskmanager.config;

import com.taskhub.taskmanager.service.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailServiceImp userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        System.out.println(">>> [JWTFilter] Incoming Auth Header: " + header);

        String username = null;
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);


            try {
                username = jwtUtil.extractUser(token);
                System.out.println(">>> [JWTFilter] Extracted Username: " + username);
            } catch (Exception e) {
                System.out.println(">>> [JWTFilter] Failed to extract user: " + e.getMessage());
            }
        } else {
            System.out.println(">>> [JWTFilter] No valid Authorization header found");
        }

        System.out.println(">>> [JWTFilter] Current SecurityContext Auth: " + SecurityContextHolder.getContext().getAuthentication());

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                //new added
                String role = jwtUtil.extractRole(token);
                role = role.replace("[", "").replace("]", "").trim();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println(">>> [JWTFilter] Authentication set for user: " + username + " with role: " + role);
            } else {
                System.out.println(">>> [JWTFilter] Token validation failed for user: " + username);
            }
        }

        filterChain.doFilter(request, response);
    }

    }




