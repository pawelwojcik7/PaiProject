package com.pai.project.controller;

import com.pai.project.model.FeeType;
import com.pai.project.model.ModelDto;
import com.pai.project.service.AppServiceService;
import com.pai.project.service.FeeService;
import com.pai.project.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController()
@RequestMapping("/game")
@RequiredArgsConstructor
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class GameController {

    private final GameService gameService;
    private final AppServiceService appServiceService;
    private final FeeService feeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    @Operation(summary = "Get all games")
    public ResponseEntity<List<ModelDto>> getAllGames() {
        return ResponseEntity.ok(gameService.getAll().stream().map(e -> new ModelDto(e.getId(), e.getName())).collect(Collectors.toList()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/download/{id}")
    @Operation(summary = "Download game")
    public ResponseEntity<?> downloadBook(@PathVariable Long id, Authentication authentication) {


        MediaType contentType = MediaType.APPLICATION_JSON;
        try {
            appServiceService.checkGameSubscriptions(authentication.getName());
            String nameForGame = gameService.getNameForGame(id).trim().strip();
            String headerValue = "attachment: filename=" + nameForGame + ".txt";
            FileInputStream fileInputStream = gameService.downloadGame(id);
            feeService.notifyFeeByDownload(authentication.getName(), FeeType.DOWNLOAD_GAME);
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header("Content-Disposition", headerValue)
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}
