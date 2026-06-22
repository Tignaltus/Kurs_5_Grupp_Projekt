package com.assignment.author_microservice.repository;

import com.assignment.author_microservice.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {
}
