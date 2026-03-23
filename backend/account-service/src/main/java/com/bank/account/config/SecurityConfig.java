package com.bank.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}") String issuerUri)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        if (issuerUri == null || issuerUri.isBlank()) {
            http.authorizeHttpRequests(
                    auth -> auth.requestMatchers("/actuator/**").permitAll().anyRequest().permitAll());
        } else {
            http.authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        }
        return http.build();
    }
}
