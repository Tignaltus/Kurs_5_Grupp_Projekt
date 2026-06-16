package com.assignment.author_microservice.dto;

public record BookResponse(
        Long id,
        String title,
        Long authorId
) {}

