package io.github.lsmcodes.spring_security.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.lsmcodes.spring_security.security.config.SecurityProperties;
import io.github.lsmcodes.spring_security.security.model.TokenObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

        public static final String AUTHORITIES = "authorities";

        public static String generate(String prefix, String key, TokenObject tokenObject) {
                String token = Jwts.builder()
                                .subject(tokenObject.getSubject())
                                .issuedAt(tokenObject.getIssuedAt())
                                .expiration(tokenObject.getExpiration())
                                .claim(AUTHORITIES, checkRoles(tokenObject.getRoles()))
                                .signWith(Keys.hmacShaKeyFor(SecurityProperties.TOKEN_KEY.getBytes()))
                                .compact();
                return SecurityProperties.TOKEN_PREFIX + token;
        }

        public static TokenObject generate(String prefix, String key, String token) {
                Claims claims = getClaimsFromToken(token);

                TokenObject tokenObject = new TokenObject()
                                .setSubject(claims.getSubject())
                                .setIssuedAt(claims.getIssuedAt())
                                .setExpiration(claims.getExpiration())
                                .setRoles((List) claims.get(AUTHORITIES));
                return tokenObject;
        }

        public static Claims getClaimsFromToken(String token) {
                token = token.replace(SecurityProperties.TOKEN_PREFIX, "");
                Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SecurityProperties.TOKEN_KEY.getBytes()))
                                .build().parseSignedClaims(token).getPayload();
                return claims;
        }

        private static List<String> checkRoles(List<String> roles) {
                return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_", ""))).collect(Collectors.toList());
        }

}