package com.example.delayservice.controllers;

import com.example.delayservice.services.DelayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "Delay configuration API", description = "")
public class DelayController {

    private final DelayService delayService;
    @Operation(summary = "Получить задержку", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/delay")
    public ResponseEntity<?> getDelay(){
        Integer res = delayService.getDelaySec();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @Operation(summary = "Изменить задержку", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PostMapping("/delay")
    public ResponseEntity<?> init(@RequestParam(name = "delay")Integer delay){
        delayService.setDelaySec(delay);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(delay);
    }
}
