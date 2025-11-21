package com.project.config;

import com.project.service.AdminDetailsService;
import com.project.service.JWTService;
import com.project.service.OfficerDetailsService;
import com.project.service.FarmerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    // ---- PUBLIC PATHS ----
    private static final Set<String> PUBLIC_URLS = Set.of(
            "/adminlogin",
            "/officerLogin",
            "/login",
            "/farmerReg",
            "/generateotp",
            "/checkvalidotp"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ---- Allow Public URLs ----
        if (PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = jwtService.extractUserName(token);
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // No token OR authentication already set → skip
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String role = jwtService.extractRole(token);

        if (role == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // ---- Load UserDetails According to Role ----
        UserDetails userDetails = switch (role) {
            case "ROLE_FARMER" -> context.getBean(FarmerDetailsService.class).loadUserByUsername(username);
            case "ROLE_OFFICER" -> context.getBean(OfficerDetailsService.class).loadUserByUsername(username);
            case "ROLE_ADMIN" -> context.getBean(AdminDetailsService.class).loadUserByUsername(username);
            default -> null;
        };

        if (userDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // ---- Validate JWT ----
        if (jwtService.validateToken(token, userDetails)) {
            var authorities = Collections.singleton(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
