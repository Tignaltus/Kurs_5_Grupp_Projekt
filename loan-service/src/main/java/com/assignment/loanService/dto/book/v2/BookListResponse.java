package com.assignment.loanService.dto.book.v2;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Versioned paged response body containing a list of books")
public class BookListResponse {

    @Schema(description = "List of books")
    private List<BookResponse> data;

    @Schema(description = "API version")
    private String version;

    @Schema(description = "Current page number, 0-based")
    private int page;

    @Schema(description = "Page size")
    private int size;

    @Schema(description = "Total number of elements")
    private long totalElements;

    @Schema(description = "Total number of pages")
    private int totalPages;

    public BookListResponse(List<BookResponse> data, String version, int page, int size, long totalElements, int totalPages) {
        this.data = data;
        this.version = version;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<BookResponse> getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}