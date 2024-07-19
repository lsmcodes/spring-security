package io.github.lsmcodes.spring_security.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.spring_security.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
        
        public User findByUsername(String username);
        public boolean existsByUsername(String username);

}