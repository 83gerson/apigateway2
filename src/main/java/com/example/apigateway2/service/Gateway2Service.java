package com.example.apigateway2.service;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Gateway2Service {

    private static final Logger logger = LoggerFactory.getLogger(Gateway2Service.class);

    @Autowired
    private RestTemplate restTemplate;

    public String forwardToAuthorization(String json) {
        logger.info("Enviando JSON a Authorization: {}", json);
        
        String authorizationServiceUrl = "https://authorization-5j80.onrender.com/api/authorization/receiveJson";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                authorizationServiceUrl,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );
        
        return Base64.getEncoder().encodeToString(responseEntity.getBody());
    }
    

    public String forwardToExternalService(String json, String externalServiceUrl) {
        logger.info("Enviando JSON a servicio externo: {}", json);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        
        return responseEntity.getBody();
    }
}