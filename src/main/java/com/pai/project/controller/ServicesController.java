package com.pai.project.controller;

import com.pai.project.entity.AppService;
import com.pai.project.service.AppServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jdk.jshell.JShell;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class ServicesController {

    private final AppServiceService appServiceService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/subscribe/{id}")
    @Operation(summary = "Subscribe service")
    public ResponseEntity<?> subscribe(@PathVariable Long id, Authentication authentication) {

        try {
            String message = appServiceService.subscribe(id, authentication.getName());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/unsubscribe")
    @Operation(summary = "Unsubscribe service")
    public ResponseEntity<?> unSubscribe(Authentication authentication) {

        try {
            appServiceService.unsubscribe(authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/services")
    @Operation(summary = "List of all services")
    public ResponseEntity<List<AppService>> getAll() {

       return ResponseEntity.ok(appServiceService.getAll());

    }

}
