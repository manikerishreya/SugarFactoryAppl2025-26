package com.project.config;

import com.project.service.AdminDetailsService;
import com.project.service.FarmerDetailsService;
import com.project.service.OfficerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired private OfficerDetailsService officerDetailsService;
    @Autowired private AdminDetailsService adminDetailsService;
    @Autowired private FarmerDetailsService farmerDetailsService;
    @Autowired private JwtFilter jwtFilter;

    // 🔥 MUST HAVE — One shared encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ==========================================
    //          AUTH PROVIDERS
    // ==========================================
    @Bean
    public AuthenticationProvider officerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(officerDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider farmerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(farmerDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ==========================================
    //          AUTHENTICATION MANAGER
    // ==========================================
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ==========================================
    //          SECURITY FILTER CHAIN
    // ==========================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/farmerReg",
                                "/login",
                                "/",
                                "/generateotp",
                                "/checkvalidotp",
                                "/records/sorted",
                                "/officerLogin",
                                "/FarmerList",
                                "/Fertilizer",
                                "/adminlogin"
                        ).permitAll()
                        .requestMatchers("/farmerProfile/**").hasRole("FARMER")
                        .requestMatchers("/officerProfile").hasRole("OFFICER")
                        .requestMatchers("/allocate/**").hasRole("OFFICER")
                        .requestMatchers("/requests/pending").hasRole("OFFICER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Register providers
        http.authenticationProvider(officerAuthProvider());
        http.authenticationProvider(adminAuthProvider());
        http.authenticationProvider(farmerAuthProvider());

        // JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ==========================================
    //          CORS CONFIG
    // ==========================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", config);
        return src;
    }
}
