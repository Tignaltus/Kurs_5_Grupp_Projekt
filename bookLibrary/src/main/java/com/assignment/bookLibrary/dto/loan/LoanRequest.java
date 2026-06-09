package com.assignment.bookLibrary.dto.loan;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for creating a loan")
public class LoanRequest {

    @Schema(description = "Id of the book to be loaned")
    @NotNull(message = "Book id must be provided")
    private Long bookId;

    public LoanRequest() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}