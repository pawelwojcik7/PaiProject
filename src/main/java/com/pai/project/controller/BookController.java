package com.pai.project.controller;

import com.pai.project.entity.Book;
import com.pai.project.model.FeeType;
import com.pai.project.model.ModelDto;
import com.pai.project.service.AppServiceService;
import com.pai.project.service.BookService;
import com.pai.project.service.FeeService;
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
@RequestMapping("/book")
@RequiredArgsConstructor
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class BookController {

    private final BookService bookService;
    private final AppServiceService appServiceService;
    private final FeeService feeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    @Operation(summary = "Get all books")
    public ResponseEntity<List<ModelDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll().stream().map(e -> new ModelDto(e.getId(), e.getName())).collect(Collectors.toList()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/download/{id}")
    @Operation(summary = "Download book")
    public ResponseEntity<?> downloadBook(@PathVariable Long id, Authentication authentication) {


        MediaType contentType = MediaType.APPLICATION_JSON;
        try {
            appServiceService.checkBookSubscriptions(authentication.getName());
            String nameForBook = bookService.getNameForBook(id).trim().strip();
            String headerValue = "attachment: filename=" + nameForBook + ".txt";
            FileInputStream fileInputStream = bookService.downloadBook(id);
            feeService.notifyFeeByDownload(authentication.getName(), FeeType.DOWNLOAD_BOOK);
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header("Content-Disposition", headerValue)
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}
