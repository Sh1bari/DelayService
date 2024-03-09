package com.example.delayservice.controllers;

import com.example.delayservice.models.Document;
import com.example.delayservice.services.DelayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> init(@RequestPart("files") List<MultipartFile> files,
                                  @ModelAttribute Document document){
        System.out.println(document.toString());
        System.out.println(files.size());
        //CompletableFuture.runAsync(() -> delayService.processWithDelay(test));

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
