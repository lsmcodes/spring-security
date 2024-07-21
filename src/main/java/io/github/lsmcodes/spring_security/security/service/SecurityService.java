package io.github.lsmcodes.spring_security.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.spring_security.model.user.User;
import io.github.lsmcodes.spring_security.repository.user.UserRepository;

@Service
public class SecurityService implements UserDetailsService {

        @Autowired
        private UserRepository repository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User foundUser = this.repository.findByUsername(username);

                Set<GrantedAuthority> authorities = new HashSet<>();
                foundUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

                UserDetails user = new org.springframework.security.core.userdetails.User(
                                foundUser.getUsername(),
                                foundUser.getPassword(),
                                authorities);

                return user;
        }

}