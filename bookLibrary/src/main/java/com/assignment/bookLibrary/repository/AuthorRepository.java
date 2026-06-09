package com.assignment.bookLibrary.repository;

import com.assignment.bookLibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
