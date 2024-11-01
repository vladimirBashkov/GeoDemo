package com.example.demo.service;
import com.example.demo.entity.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeolocationService {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=%s&lon=%s";

    public String getResponseGeo(String request) throws IOException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(request);
            Double[] startPos = {rootNode.get("startPos").get(0).asDouble(), rootNode.get("startPos").get(1).asDouble()};
            Double[] endPos = {rootNode.get("endPos").get(0).asDouble(), rootNode.get("endPos").get(1).asDouble()};
            String startAddress = getAddressFromCoordinates(startPos[0], startPos[1]);
            String endAddress = getAddressFromCoordinates(endPos[0], endPos[1]);
            long distance = calculateDistance(startPos[0], startPos[1], endPos[0], endPos[1]);

            ResponseEntity response = new ResponseEntity();
            response.setStartPos(startPos);
            response.setEndPos(endPos);
            response.setStartAddress(startAddress);
            response.setEndAddress(endAddress);
            response.setDistance(distance);

            return mapper.writeValueAsString(response);
        } catch (NullPointerException ex){
            throw new IOException("Nullable data");
        }

    }

    // Метод для получения адреса по координатам с использованием Nominatim API
    private String getAddressFromCoordinates(double latitude, double longitude) throws IOException {
        if(!checkLatitude(latitude) || !checkLongitude(longitude)){
            throw new IOException("Wrong data");
        }
        String lat = Double.toString(latitude).replace(",", ".");
        String lon = Double.toString(longitude).replace(",", ".");
        String url = String.format(NOMINATIM_URL, lat, lon);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", "JavaDemoGeoLocationApp");

            HttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            return rootNode.has("display_name") ? rootNode.get("display_name").asText() : "Address not found";
        }
    }

    private boolean checkLatitude(double latitude){
        return latitude >= -90L && latitude <= 90L;
    }

    private boolean checkLongitude(double longitude){
        return longitude >= -180L && longitude <= 180L;
    }

    // Универсальная формула для расчета расстояния между двумя точками на поверхности
    // Земли называется формулой Хаверсина (Haversine Formula).
    //Она учитывает кривизну Земли и используется для вычисления расстояния
    // по дуге большого круга (геодезического расстояния)
    // между двумя точками, заданными их широтой и долготой.
    private long calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Радиус Земли в метрах
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c);
    }
}
