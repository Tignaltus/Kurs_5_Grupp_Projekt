package com.assignment.loanService.client;

import com.assignment.loanService.dto.book.v1.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/v1/books/{id}")
    BookDTO getBookById(@PathVariable Long id);
}
