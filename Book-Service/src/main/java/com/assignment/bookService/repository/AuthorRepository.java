package com.assignment.bookService.repository;

import com.assignment.bookService.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}