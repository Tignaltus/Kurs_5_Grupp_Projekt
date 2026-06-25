package com.assignment.bookService.repository;

import com.assignment.bookService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}