package io.github.lsmcodes.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/welcome")
public class WelcomeController {

        @GetMapping
        public String welcome() {
                return "Welcome to my Spring Boot Web API!";
        }

        @GetMapping("/users")
        @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        public String users() {
                return "Authorized user or admin";
        }

        @GetMapping("/admins")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String admins() {
                return "Authorized admin";
        }

}