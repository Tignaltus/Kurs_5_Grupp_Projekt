package com.assignment.author_microservice.service;

import com.assignment.author_microservice.dto.AuthorRequest;
import com.assignment.author_microservice.dto.AuthorResponse;
import com.assignment.author_microservice.exception.AuthorNotFoundException;
import com.assignment.author_microservice.model.Author;
import com.assignment.author_microservice.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author(request.getName());
        Author savedAuthor = authorRepository.save(author);
        return new AuthorResponse(savedAuthor.getId(), savedAuthor.getName(), 0);
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found"));

        return new AuthorResponse(author.getId(), author.getName(), 0);
    }
}
