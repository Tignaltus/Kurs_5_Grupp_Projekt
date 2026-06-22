package com.assignment.author_microservice.client;

import com.assignment.author_microservice.dto.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-microservice")
public interface BookClient {

    @GetMapping("/api/v1/books/author/{authorId}")
    List<BookResponse> getBooksByAuthor(@PathVariable Long authorId);
}
