package com.example.demo;

import com.example.demo.service.GeolocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeolocationServiceTest {
    GeolocationService service = new GeolocationService();

    @Test
    @DisplayName("Тестовый запрос на расстояние и адреса")
    void getData_ValidRequest_ReturnsResponse() throws IOException {
        String request = "{ \"startPos\": [45.062609, 41.923656], \"endPos\": [57.165054, 65.498056] }";

        var result = this.service.getResponseGeo(request);

        String response = """
                {"startPos":[45.062609,41.923656],"endPos":[57.165054,65.498056],"startAddress":"29Д, проспект Кулакова, Северо-Западный, Промышленный район, Ставрополь, городской округ Ставрополь, Ставропольский край, Северо-Кавказский федеральный округ, 355000, Россия","endAddress":"84, улица Свободы, Калининский административный округ, Тюмень, городской округ Тюмень, Тюменская область, Уральский федеральный округ, 625001, Россия","distance":2106981}""";
        assertEquals(response, result);
    }

    @Test
    @DisplayName("Тестовый запрос на расстояние и адреса с неверными данными")
    void getData_NoValidRequest_ReturnsResponse() throws IOException {
        String request = "{ \"startPos\": [245.062609, 41.923656], \"endPos\": [57.165054, 65.498056] }";
        assertThrows(IOException.class, () -> {
            this.service.getResponseGeo(request);
        });
    }



}
