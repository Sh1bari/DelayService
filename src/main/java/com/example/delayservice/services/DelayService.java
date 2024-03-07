package com.example.delayservice.services;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class DelayService {

    private Integer delaySec = 3;
    private final RestTemplate restTemplate;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void processWithDelay(String data) {
        // Задержка
        executorService.schedule(() -> {
            // Логика обработки данных
            System.out.println("Processed data after delay: " + data);
        }, delaySec, TimeUnit.SECONDS);
    }

    public void postRequest(String url, String data) {
        // Устанавливаем заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем объект HttpEntity с телом запроса и заголовками
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);

        // Выполняем POST-запрос
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Выводим результат запроса
        System.out.println("HTTP POST Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }

    public void getRequest(String url) {
        // Выполняем GET-запрос
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Выводим результат запроса
        System.out.println("HTTP GET Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }
    public Integer getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(Integer delaySec) {
        this.delaySec = delaySec;
    }
}
