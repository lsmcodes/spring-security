package io.github.lsmcodes.spring_security.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.lsmcodes.spring_security.security.config.SecurityProperties;
import io.github.lsmcodes.spring_security.security.model.TokenObject;
import io.github.lsmcodes.spring_security.security.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {
                String token = request.getHeader(SecurityProperties.HEADER_AUTHORIZATION);

                try {
                        if ((token != null) && (!token.isEmpty())) {
                                TokenObject tokenObject = TokenService.generate(SecurityProperties.TOKEN_PREFIX,
                                                SecurityProperties.TOKEN_KEY,
                                                token);

                                List<SimpleGrantedAuthority> authorities = getGrantedAuthorities(
                                                tokenObject.getRoles());

                                UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(
                                                tokenObject.getSubject(), null, authorities);

                                SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
                        } else {
                                SecurityContextHolder.clearContext();
                        }
                } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                        e.printStackTrace();
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        return;
                }

                filterChain.doFilter(request, response);
        }

        private List<SimpleGrantedAuthority> getGrantedAuthorities(List<String> roles) {
                return roles.stream().map(SimpleGrantedAuthority::new).toList();
        }

}