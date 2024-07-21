package io.github.lsmcodes.spring_security.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityProperties {

        public static String TOKEN_KEY;
        public static Long TOKEN_EXPIRATION;
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_AUTHORIZATION = "Authorization";

        public void setKEY(String tokenKey) {
                TOKEN_KEY = tokenKey;
        }

        public void setEXPIRATION(Long tokenExpiration) {
                TOKEN_EXPIRATION = tokenExpiration;
        }

}