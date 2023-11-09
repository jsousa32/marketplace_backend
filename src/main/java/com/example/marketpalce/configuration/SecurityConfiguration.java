package com.example.marketpalce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The Class MethodConfiguration
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        http ->
                                http
                                        .requestMatchers("swagger-ui/**").permitAll()
                                        .requestMatchers("v3/api-docs/**").permitAll()
                                        .anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 ->
                                oauth2.jwt(j -> j.jwtAuthenticationConverter(new KeycloakConverter())));

        return httpSecurity.build();
    }
}
