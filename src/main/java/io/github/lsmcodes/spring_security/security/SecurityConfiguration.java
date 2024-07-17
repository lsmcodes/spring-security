package io.github.lsmcodes.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.withUsername("user").password("{noop}user1234").roles("USER").build();

                UserDetails admin = User.withUsername("admin").password("{noop}admin1234").roles("USER", "ADMIN").build();

                return new InMemoryUserDetailsManager(user, admin);
        }

}