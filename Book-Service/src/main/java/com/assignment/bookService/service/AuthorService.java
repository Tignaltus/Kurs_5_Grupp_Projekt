package com.assignment.bookService.service;

import com.assignment.bookService.model.Author;
import com.assignment.bookService.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setCountry(updatedAuthor.getCountry());
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}