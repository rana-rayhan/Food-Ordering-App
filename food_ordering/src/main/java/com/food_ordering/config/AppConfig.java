package com.food_ordering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.food_ordering.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepo userRepo;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Dose not exist with this email !"));
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        CustomAuthProvider provider = new CustomAuthProvider(userDetailsService(),
                passwordEncoder());
        return provider;

        // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // provider.setUserDetailsService(userDetailsService());
        // provider.setPasswordEncoder(passwordEncoder());
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
