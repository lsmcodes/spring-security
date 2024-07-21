package io.github.lsmcodes.spring_security.security.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.spring_security.model.user.User;
import io.github.lsmcodes.spring_security.security.config.SecurityProperties;
import io.github.lsmcodes.spring_security.security.dto.LoginDTO;
import io.github.lsmcodes.spring_security.security.dto.TokenDTO;
import io.github.lsmcodes.spring_security.security.model.TokenObject;
import io.github.lsmcodes.spring_security.security.service.TokenService;
import io.github.lsmcodes.spring_security.service.user.UserService;

@RestController
@RequestMapping("api/users")
public class AuthController {

        @Autowired
        private UserService userService;

        @PostMapping("/login")
        public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {
                User user = this.userService.getUserByUsername(dto.getUsername());

                if (user != null) {
                        boolean correctPassword = this.userService.verifyIfUserPasswordIsCorrect(dto.getPassword(),
                                        user.getPassword());

                        if (!correctPassword) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                        }

                        TokenObject tokenObject = new TokenObject()
                                        .setSubject(user.getUsername())
                                        .setIssuedAt(new Date(System.currentTimeMillis()))
                                        .setExpiration(new Date(
                                                        System.currentTimeMillis()
                                                                        + SecurityProperties.TOKEN_EXPIRATION))
                                        .setRoles(user.getRoles());

                        String token = TokenService.generate(SecurityProperties.TOKEN_PREFIX,
                                        SecurityProperties.TOKEN_KEY, tokenObject);

                        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token));
                }

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

}