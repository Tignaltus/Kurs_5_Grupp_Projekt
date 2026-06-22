package com.assignment.author_microservice.service;

import com.assignment.author_microservice.client.BookClient;
import com.assignment.author_microservice.dto.AuthorRequest;
import com.assignment.author_microservice.dto.AuthorResponse;
import com.assignment.author_microservice.dto.AuthorWithBooksResponse;
import com.assignment.author_microservice.dto.BookResponse;
import com.assignment.author_microservice.exception.AuthorNotFoundException;
import com.assignment.author_microservice.model.Author;
import com.assignment.author_microservice.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookClient bookClient;   // ⭐ Feign‑klient injicerad

    public AuthorService(AuthorRepository authorRepository, BookClient bookClient) {
        this.authorRepository = authorRepository;
        this.bookClient = bookClient;
    }

    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author(request.getName());
        Author savedAuthor = authorRepository.save(author);

        return new AuthorResponse(
                savedAuthor.getId(),
                savedAuthor.getName(),
                0   // bookCount (kan uppdateras senare)
        );
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author with id " + id + " not found"));

        return new AuthorResponse(
                author.getId(),
                author.getName(),
                0
        );
    }

    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(author -> new AuthorResponse(
                        author.getId(),
                        author.getName(),
                        0
                ))
                .toList();
    }

    public AuthorWithBooksResponse getAuthorWithBooks(Long authorId) {

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found"));

        List<BookResponse> books;

        try {
            // Försök hämta böcker från Book‑service
            books = bookClient.getBooksByAuthor(authorId);
        } catch (Exception e) {
            // Om Book‑service inte finns ännu → returnera tom lista
            books = List.of();
        }

        return new AuthorWithBooksResponse(
                author.getId(),
                author.getName(),
                books
        );
    }
}
