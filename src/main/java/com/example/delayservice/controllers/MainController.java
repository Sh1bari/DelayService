package com.example.delayservice.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Delay API", description = "")
public class MainController {

    @PostMapping("/init-periods")
    public ResponseEntity<?> init(@RequestParam(name = "file")MultipartFile file){

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
