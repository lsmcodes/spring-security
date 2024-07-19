package io.github.lsmcodes.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/api/users/new").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/welcome").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/welcome/users").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/welcome/admins").hasRole("ADMIN")
                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf.disable())
                                .httpBasic(Customizer.withDefaults())
                                .build();
        }

}