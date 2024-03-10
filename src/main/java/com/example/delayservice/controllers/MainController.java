package com.example.delayservice.controllers;

import com.example.delayservice.models.Document;
import com.example.delayservice.services.DelayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Resend API", description = "")
public class MainController {

    private final DelayService delayService;
    @PostMapping("/send-to-earth")
    public ResponseEntity<?> init(@RequestPart(name = "files", required = false) List<MultipartFile> files,
                                  @ModelAttribute Document document){

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    // Используйте ByteArrayResource для передачи файла
                    ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    };
                    body.add("files", resource);
                } catch (Exception e) {
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

        CompletableFuture.runAsync(() -> delayService.sendToEarthWithDelay(requestEntity));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
