package io.github.lsmcodes.spring_security.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lsmcodes.spring_security.dto.user.UserDTO;
import io.github.lsmcodes.spring_security.model.user.User;
import io.github.lsmcodes.spring_security.service.user.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

        @Autowired
        private UserService service;

        @PostMapping("/new")
        public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO dto) {
                User foundUser = this.service.getUserByUsername(dto.getUsername());

                if (foundUser == null) {
                        User user = new User();
                        user.setName(dto.getName());
                        user.setUsername(dto.getUsername());

                        String encodedPassword = this.service.encodeUserPassword(dto.getPassword());
                        user.setPassword(encodedPassword);
                        
                        dto.getRoles().stream().forEach(role -> user.getRoles().add(role));

                        User savedUser = this.service.saveUser(user);
                        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(savedUser));
                }

                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

}