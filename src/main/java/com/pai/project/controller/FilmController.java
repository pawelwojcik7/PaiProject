package com.pai.project.controller;

import com.pai.project.entity.Book;
import com.pai.project.entity.Film;
import com.pai.project.model.FeeType;
import com.pai.project.model.ModelDto;
import com.pai.project.service.AppServiceService;
import com.pai.project.service.FeeService;
import com.pai.project.service.FilmService;
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
@RequestMapping("/film")
@RequiredArgsConstructor
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class FilmController {

    private final FilmService filmService;
    private final AppServiceService appServiceService;
    private final FeeService feeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    @Operation(summary = "Get all films")
    public ResponseEntity<List<ModelDto>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAll().stream().map(e -> new ModelDto(e.getId(), e.getName())).collect(Collectors.toList()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/download/{id}")
    @Operation(summary = "Download film")
    public ResponseEntity<?> downloadBook(@PathVariable Long id, Authentication authentication) {


        MediaType contentType = MediaType.APPLICATION_JSON;
        try {
            appServiceService.checkBookSubscriptions(authentication.getName());
            String nameForFilm = filmService.getNameForFilm(id).trim().strip();
            String headerValue = "attachment: filename=" + nameForFilm + ".txt";
            FileInputStream fileInputStream = filmService.downloadFilm(id);
            feeService.notifyFeeByDownload(authentication.getName(), FeeType.DOWNLOAD_FILM);
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header("Content-Disposition", headerValue)
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}
