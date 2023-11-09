package com.example.marketpalce.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The Class KeycloakConverter
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public class KeycloakConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwtKeycloak) {

        Map<String, Collection<String>> realmAccess = jwtKeycloak.getClaim("realm_access");
        Collection<String> rolesKeycloak = realmAccess.get("roles");

        List<SimpleGrantedAuthority> listGrants = rolesKeycloak
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.toUpperCase()))).toList();

        return new JwtAuthenticationToken(jwtKeycloak, listGrants);
    }
}
