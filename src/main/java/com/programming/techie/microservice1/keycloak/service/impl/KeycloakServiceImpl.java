package com.programming.techie.microservice1.keycloak.service.impl;

import com.programming.techie.microservice1.keycloak.service.KeycloakService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Autowired()
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    String realm;

    RealmResource realmResource;

    @PostConstruct
    public void init() {
        this.realmResource = keycloak.realm(realm);
    }

    @Override
    public Response createUser(UserRepresentation userRepresentation) {
        return realmResource.users().create(userRepresentation);
    }

    @Override
    public void assignRoles(Map<String, List<String>> roles, String username) {
        String userId = realmResource.users().search(username).get(0).getId();
        RolesResource rolesRealm = realmResource.roles();

        UserResource userResource = realmResource.users().get(userId);
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : roles.entrySet()) {
            List<String> roleNames = entry.getValue();

            List<RoleRepresentation> clientRoles = rolesRealm.list();

            for (RoleRepresentation role : clientRoles) {
                if (roleNames.contains(role.getName())) {
                    roleRepresentations.add(role);
                }
            }
        }
        for (RoleRepresentation role : roleRepresentations) {
            userResource.roles().realmLevel().add(Collections.singletonList(role));
        }
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return realmResource.users().get(userId).toRepresentation();
    }
}
