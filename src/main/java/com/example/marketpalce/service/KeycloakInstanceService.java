package com.example.marketpalce.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Class KeycloakInstanceService
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Service
public class KeycloakInstanceService {

    @Value("${env.keycloak.urlBase}")
    private String urlbase;

    @Value("${env.keycloak.admin.secret}")
    private String clientSecretAdmin;

    @Value("${env.keycloak.admin.clientId}")
    private String clientIdAdmin;

    @Value("${env.keycloak.admin.username}")
    private String usernameAdmin;

    @Value("${env.keycloak.admin.password}")
    private String passwordAdmin;

    @Value("${env.keycloak.admin.realm}")
    private String realmAdmin;

    @Value("${env.keycloak.user.realm}")
    private String realmUser;

    @Value("${env.keycloak.user.clientId}")
    private String clientIdUser;

    @Value("${env.keycloak.user.secret}")
    private String clientSecretUser;

    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .realm(realmAdmin)
                .serverUrl(urlbase)
                .clientId(clientIdAdmin)
                .password(passwordAdmin)
                .username(usernameAdmin)
                .clientSecret(clientSecretAdmin)
                .build();
    }

    public UsersResource getUserResource() {
        Keycloak keycloak = getInstance();
        RealmResource realmResource = keycloak.realm(realmUser);

        return realmResource.users();
    }

    public AccessTokenResponse getAccessToken(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realmUser)
                .serverUrl(urlbase)
                .clientId(clientIdUser)
                .username(username)
                .password(password)
                .clientSecret(clientSecretUser)
                .build()
                .tokenManager()
                .getAccessToken();
    }

    public List<RoleRepresentation> getRealmRoles() {
        Keycloak keycloak = getInstance();

        return keycloak.realm(realmUser).roles().list();
    }
}
