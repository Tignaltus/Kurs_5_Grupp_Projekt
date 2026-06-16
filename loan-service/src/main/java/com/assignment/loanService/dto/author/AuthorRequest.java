package com.assignment.loanService.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating an author")
public class AuthorRequest {

    @Schema(description = "Name of the author")
    @NotBlank(message = "Author name must not be blank")
    @Size(min = 1, max = 50, message = "Author name must be between 1 and 50 characters")
    private String name;

    public AuthorRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}