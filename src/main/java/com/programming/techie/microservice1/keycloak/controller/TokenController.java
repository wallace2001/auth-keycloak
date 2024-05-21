package com.programming.techie.microservice1.keycloak.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/token")
@RestController
public class TokenController {

    @Value("${keycloak.clientSecret}")
    String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri-token}")
    String uriToken;

    @PostMapping("/")
    public ResponseEntity<String> token(@RequestBody User user) {


        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("username", user.username);
        formData.add("password", user.password);
        formData.add("grant_type", user.grantType);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(formData, headers);

        var result = rt.postForEntity(uriToken, entity, String.class);

        return result;
    }

    public record User(String password, String clientId, String grantType, String username) {}

}