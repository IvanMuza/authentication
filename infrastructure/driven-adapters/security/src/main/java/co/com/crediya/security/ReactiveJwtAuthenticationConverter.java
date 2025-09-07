package co.com.crediya.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class ReactiveJwtAuthenticationConverter implements Converter<
        Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtRoleConverter jwtRoleConverter;

    public ReactiveJwtAuthenticationConverter(JwtRoleConverter jwtRoleConverter) {
        this.jwtRoleConverter = jwtRoleConverter;
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwtRoleConverter.convert(jwt);
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }
}