package com.pai.project.controller;

import com.pai.project.model.ChangePasswordDto;
import com.pai.project.model.JwtRequest;
import com.pai.project.model.JwtResponse;
import com.pai.project.model.UserDto;
import com.pai.project.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@OpenAPIDefinition
@ApiOperation("Auth API")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping(value = "/authenticate")
    @Operation(summary = "Logowanie użytkownika - wygenerowanie tokena")
    public ResponseEntity<?> loginAppUser(@RequestBody JwtRequest authRequest) {

        try {
            JwtResponse jwtResponse = userService.authenticate(authRequest.getLogin(), authRequest.getPassword());
            return ResponseEntity.ok().body(jwtResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(value = "/register")
    @Operation(summary = "Rejestracja użytkownika")
    public ResponseEntity<?> registerAppUser(@RequestBody UserDto user) {

        try {
            userService.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/changePassword")
    @Operation(summary = "Zmiana hasła użytkownika aktualnego tokena")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto, Authentication authentication) {

        try {
            userService.authenticate(authentication.getName(),
                    changePasswordDto.getOldPassword());
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        try {
            userService.changePassword(authentication.getName(), changePasswordDto.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
