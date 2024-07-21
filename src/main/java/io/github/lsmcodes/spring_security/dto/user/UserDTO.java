package io.github.lsmcodes.spring_security.dto.user;

import java.util.List;

import io.github.lsmcodes.spring_security.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

        private Long id;
        private String name;
        private String username;
        private String password;
        private List<String> roles;

        public UserDTO(User user) {
                this.id = user.getId();
                this.name = user.getName();
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.roles = user.getRoles();
        }

}