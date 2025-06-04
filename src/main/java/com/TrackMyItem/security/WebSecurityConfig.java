package com.TrackMyItem.security;

import com.TrackMyItem.config.CORSConfig;
import com.TrackMyItem.security.jwt.AuthEntryPoint;
import com.TrackMyItem.security.jwt.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AuthEntryPoint authEntryPoint;
    private final AuthFilter authFilter;
    private final CORSConfig corsConfig;


    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userDetailsService);
        dap.setPasswordEncoder(passwordEncoder());
        return dap;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors( cors-> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/auth/changePassword").hasAnyRole("USER,ADMIN,STAFF")

                                .requestMatchers(HttpMethod.POST, "/api/v1/items").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/items").hasRole("USER")

                                .requestMatchers(HttpMethod.POST, "/api/v1/requests").hasRole("USER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/requests").hasRole("STAFF")

                                .requestMatchers(HttpMethod.POST, "/api/v1/admins").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/staffs").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
