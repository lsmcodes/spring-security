package io.github.lsmcodes.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.withUsername("user").password("{noop}user1234").roles("USER").build();

                UserDetails admin = User.withUsername("admin").password("{noop}admin1234").roles("USER", "ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(user, admin);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/welcome").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/welcome/admins").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "api/welcome/users").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                                .formLogin(Customizer.withDefaults())
                                .build();
        }

}