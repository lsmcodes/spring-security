package io.github.lsmcodes.spring_security.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.lsmcodes.spring_security.security.filter.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .addFilterAfter(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/h2-console/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/users/new").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/welcome").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/welcome/users")
                                                .hasAnyRole("USER", "ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/api/welcome/admins")
                                                .hasAnyRole("ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .headers(headers -> headers.frameOptions(options -> options.disable()))
                                .build();
        }

}