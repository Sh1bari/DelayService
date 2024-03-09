package com.example.delayservice.services;

import com.example.delayservice.models.Document;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class DelayService {

    @Value("${host}")
    private String host;

    private Integer delaySec = 3;
    private final RestTemplate restTemplate;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void sendToEarthWithDelay(List<MultipartFile> files, Document document) {
        // Задержка
        executorService.schedule(() -> {
            sendToEarth(host + "/api/document/send-to-earth",
                    files,
                    document);
        }, delaySec, TimeUnit.SECONDS);
    }

    public void sendToEarth(String url, List<MultipartFile> files, Document document) {
        // Создание объекта RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Подготовка параметров запроса
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    };
                    body.add("files", resource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Добавление остальных параметров в параметры запроса
        body.add("id", document.getId());
        body.add("title", document.getTitle());
        body.add("owner", document.getOwner());
        body.add("sentTime", document.getSentTime());
        body.add("createdAt", document.getCreatedAt());
        body.add("payload", document.getPayload());

        // Создание объекта HttpEntity для передачи параметров
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Отправка POST-запроса
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Обработка ответа (если необходимо)
        System.out.println("Response: " + response.getBody());
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
