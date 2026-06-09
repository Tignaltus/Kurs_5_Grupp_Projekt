package com.assignment.bookLibrary.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for an author")
public class AuthorResponse {

    @Schema(description = "Author id")
    private Long id;

    @Schema(description = "Name of the author")
    private String name;

    @Schema(description = "Number of books connected to the author")
    private int bookCount;

    public AuthorResponse(Long id, String name, int bookCount) {
        this.id = id;
        this.name = name;
        this.bookCount = bookCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBookCount() {
        return bookCount;
    }
}