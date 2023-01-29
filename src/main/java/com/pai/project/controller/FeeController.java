package com.pai.project.controller;

import com.pai.project.entity.Fee;
import com.pai.project.service.FeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/fee")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    @Operation(summary = "Get all my fees")
    public ResponseEntity<List<Fee>> getAll(Authentication authentication) {

        return ResponseEntity.ok(feeService.getAllForUser(authentication.getName()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/sum")
    @Operation(summary = "Get sum of my fees")
    public ResponseEntity<Double> getSum(Authentication authentication) {

        return ResponseEntity.ok(feeService.getAllForUser(authentication.getName()).stream().map(Fee::getPrice).reduce((double) 0, Double::sum));
    }
}
