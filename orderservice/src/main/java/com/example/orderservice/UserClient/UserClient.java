package com.example.orderservice.UserClient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${user.service.url}")
    private String userServiceUrl;

    public boolean userExists(Long userId) {
        try {
            restTemplate.getForEntity(
                    userServiceUrl + "/api/users/" + userId + "/exists",
                    Void.class
            );
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}

