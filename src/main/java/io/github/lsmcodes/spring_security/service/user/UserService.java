package io.github.lsmcodes.spring_security.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.spring_security.model.user.User;
import io.github.lsmcodes.spring_security.repository.user.UserRepository;

@Service
public class UserService {

        @Autowired
        UserRepository repository;

        @Autowired
        BCryptPasswordEncoder encoder;

        public User getUserByUsername(String username) {
                return this.repository.findByUsername(username);
        }

        public User saveUser(User user) {
                return this.repository.save(user);
        }

        public String encodeUserPassword(String password) {
                return encoder.encode(password);
        }

}