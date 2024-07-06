package com.example.apigateway2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apigateway2.service.Gateway2Service;

@RestController
@RequestMapping("/api/gateway2")
public class Gateway2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Gateway2Controller.class);

    @Autowired
    private Gateway2Service gateway2Service;

    @PostMapping(value = "/receive", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> receive(@RequestBody String json) {
        logger.info("JSON recibido en Apigateway2: {}", json);
        String externalServiceUrl = "https://ghostwhite-termite-821582.hostingersite.com/api/controlador/GestionAutorizacion.php";
        String response = gateway2Service.forwardToExternalService(json, externalServiceUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sendJson", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> sendJson(@RequestBody String json) {
        String response = gateway2Service.forwardToAuthorization(json);
        return ResponseEntity.ok(response);
    }
}
