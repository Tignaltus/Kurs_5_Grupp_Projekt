package com.assignment.loanService.dto.book.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Response body for a book in API version 1")
public class BookResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Book id")
    private Long id;

    @Schema(description = "Title of the book")
    private String title;

    @Schema(description = "ISBN number of the book")
    private String isbn;

    @Schema(description = "Year the book was published")
    private int publishedYear;

    @Schema(description = "Name of the author")
    private String authorName;

    public BookResponse(Long id, String title, String isbn, int publishedYear, String authorName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.authorName = authorName;
    }

    //<editor-fold desc="Getters">

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getAuthorName() {
        return authorName;
    }

    //</editor-fold>
}