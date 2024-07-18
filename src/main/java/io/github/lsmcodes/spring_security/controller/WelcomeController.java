package io.github.lsmcodes.spring_security.controller;

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
        public String users() {
                return "Authorized user or admin";
        }

        @GetMapping("/admins")
        public String admins() {
                return "Authorized admin";
        }

}