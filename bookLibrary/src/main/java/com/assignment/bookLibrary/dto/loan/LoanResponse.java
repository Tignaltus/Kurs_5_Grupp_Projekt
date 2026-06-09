package com.assignment.bookLibrary.dto.loan;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response body for a loan")
public class LoanResponse {

    @Schema(description = "Loan id")
    private Long id;

    @Schema(description = "Id of the borrowed book")
    private Long bookId;

    @Schema(description = "Title of the borrowed book")
    private String bookTitle;

    @Schema(description = "Date when the loan was created")
    private LocalDate loanDate;

    @Schema(description = "Date when the book was returned, null if still active")
    private LocalDate returnDate;

    public LoanResponse(Long id, Long bookId, String bookTitle, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}