package com.assignment.author_microservice.dto;

import java.util.List;

public record AuthorWithBooksResponse(
        Long id,
        String name,
        List<BookResponse> books
) {}

