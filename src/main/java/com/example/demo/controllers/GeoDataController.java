package com.example.demo.controllers;


import com.example.demo.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/api")
public class GeoDataController {

    @Autowired
    GeolocationService geolocationService;

    @PostMapping(path = "/length", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLength(HttpEntity<String> httpEntity){
        String request = httpEntity.getBody();
        try {
            return ResponseEntity.ok().body(geolocationService.getResponseGeo(request));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().body("error on request");
        }
    }

}
