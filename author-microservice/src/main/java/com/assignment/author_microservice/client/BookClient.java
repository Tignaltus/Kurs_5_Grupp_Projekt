package com.assignment.author_microservice.client;

import com.assignment.author_microservice.dto.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/v1/books")
    List<BookResponse> getAllBooks();
}