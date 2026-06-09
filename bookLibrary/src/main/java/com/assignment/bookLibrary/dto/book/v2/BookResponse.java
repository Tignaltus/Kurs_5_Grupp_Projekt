package com.assignment.bookLibrary.dto.book.v2;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for a book in API version 2")
public class BookResponse {

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

    @Schema(description = "Whether the book is currently available for loan")
    private boolean available;

    public BookResponse(Long id, String title, String isbn, int publishedYear, String authorName, boolean available) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.authorName = authorName;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }

    //</editor-fold">
}