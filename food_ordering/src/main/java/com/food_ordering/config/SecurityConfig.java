package com.food_ordering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
        private final AuthenticationProvider authenticationProvider;
        private final JwtFilter jwtFilter;
        private final JwtAuthEntiryPoint jwtAuthEntiryPoint;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(c -> c.disable())
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers("/api/admin/**")
                                                .hasAnyRole("RESTAURENT_OWNER", "ADMIN")
                                                .requestMatchers("/api/**").authenticated()
                                                .anyRequest().permitAll())
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntiryPoint))
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()));

                return http.build();
        }

        // @Bean
        // CorsConfigurationSource corsConfigurationSource() {
        // CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("http://localhost:6000"));
        // configuration.setAllowedMethods(Collections.singletonList("*"));
        // configuration.setAllowCredentials(true);
        // configuration.setAllowedHeaders(Collections.singletonList("*"));
        // configuration.setExposedHeaders(Arrays.asList("Authorization"));
        // configuration.setMaxAge(3600L);

        // UrlBasedCorsConfigurationSource source = new
        // UrlBasedCorsConfigurationSource();
        // source.registerCorsConfiguration("/**", configuration);

        // return source;
        // }
}
