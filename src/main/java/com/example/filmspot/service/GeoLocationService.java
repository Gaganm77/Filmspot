package com.example.filmspot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeoLocationService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s";

    public String getCityFromCoordinates(Double latitude, Double longitude) {
        String url = String.format(NOMINATIM_URL, latitude, longitude);
        RestTemplate restTemplate = new RestTemplate();
        Map<?, ?> response = restTemplate.getForObject(url, Map.class);
        return response != null ? (String) ((Map<?, ?>) response.get("address")).get("city") : null;
    }
}