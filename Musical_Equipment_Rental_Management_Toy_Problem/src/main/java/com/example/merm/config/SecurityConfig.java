package com.example.merm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // Allow all requests to the H2 console for development purposes
                .requestMatchers("/h2-console/**").permitAll()
                // Any other request requires authentication
                .anyRequest().authenticated())
            // Enable HTTP Basic authentication
            .httpBasic(httpBasic -> httpBasic
            		.realmName("Custom Realm"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password("{noop}password")  // Using {noop} prefix to indicate plain text passwords for backward compatibility
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}

