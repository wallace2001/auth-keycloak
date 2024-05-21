package com.programming.techie.microservice1.Beans;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class KeycloakBean {

    @Value("${keycloak.serverUrl}")
    String serverUrl;

    @Value("${keycloak.realm}")
    String realm;

    @Value("${keycloak.clientId}")
    String clientId;

    @Value("${keycloak.clientSecret}")
    String clientSecret;

    @Bean()
    public Keycloak keycloak() {

        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
