package com.assignment.author_microservice.controller;

import com.assignment.author_microservice.dto.AuthorRequest;
import com.assignment.author_microservice.dto.AuthorResponse;
import com.assignment.author_microservice.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Authors", description = "Endpoints for managing authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }
}
