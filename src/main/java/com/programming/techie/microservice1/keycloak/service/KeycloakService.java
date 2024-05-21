package com.programming.techie.microservice1.keycloak.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Map;

public interface KeycloakService {
    Response createUser(UserRepresentation userRepresentation);

    void assignRoles(Map<String, List<String>> roles, String username);

    UserRepresentation getUserById(String userId);
}
