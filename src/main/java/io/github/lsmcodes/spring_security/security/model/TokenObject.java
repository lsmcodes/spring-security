package io.github.lsmcodes.spring_security.security.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenObject {

        private String subject;
        private Date issuedAt;
        private Date expiration;
        private List<String> roles;

}