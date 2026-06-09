package com.assignment.bookLibrary;

import com.assignment.bookLibrary.repository.AuthorRepository;
import com.assignment.bookLibrary.repository.BookRepository;
import com.assignment.bookLibrary.repository.LoanRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void createAuthor_andGetAuthorById_returnsAuthor() throws Exception {
        Long authorId = createAuthor("J.K. Rowling");

        mockMvc.perform(get("/api/v1/authors/{id}", authorId)
                        .with(httpBasic("libraryuser", "librarypass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").value("J.K. Rowling"))
                .andExpect(jsonPath("$.bookCount").value(0));
    }

    @Test
    void createBook_andGetBookById_andGetBooksV2_returnsAvailableTrue() throws Exception {
        Long authorId = createAuthor("J.R.R. Tolkien");
        Long bookId = createBook(authorId, "The Hobbit", "9780261103344", 1937);

        mockMvc.perform(get("/api/v1/books/{id}", bookId)
                        .with(httpBasic("libraryuser", "librarypass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("The Hobbit"))
                .andExpect(jsonPath("$.authorName").value("J.R.R. Tolkien"));

        mockMvc.perform(get("/api/v2/books")
                        .with(httpBasic("libraryuser", "librarypass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("v2"))
                .andExpect(jsonPath("$.data[0].id").value(bookId))
                .andExpect(jsonPath("$.data[0].title").value("The Hobbit"))
                .andExpect(jsonPath("$.data[0].available").value(true));
    }

    @Test
    void createLoan_twiceForSameBook_returnsBadRequest_andBookBecomesUnavailable() throws Exception {
        Long authorId = createAuthor("George Orwell");
        Long bookId = createBook(authorId, "1984", "9780451524935", 1949);

        mockMvc.perform(post("/api/v1/loans")
                        .with(httpBasic("libraryuser", "librarypass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("bookId", bookId))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.bookTitle").value("1984"));

        mockMvc.perform(post("/api/v1/loans")
                        .with(httpBasic("libraryuser", "librarypass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("bookId", bookId))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book is already on loan"));

        mockMvc.perform(get("/api/v2/books")
                        .with(httpBasic("libraryuser", "librarypass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].available").value(false));
    }

    private Long createAuthor(String name) throws Exception {
        String response = mockMvc.perform(post("/api/v1/authors")
                        .with(httpBasic("libraryuser", "librarypass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", name))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asLong();
    }

    private Long createBook(Long authorId, String title, String isbn, int publishedYear) throws Exception {
        String response = mockMvc.perform(post("/api/v1/books")
                        .with(httpBasic("libraryuser", "librarypass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "title", title,
                                "isbn", isbn,
                                "publishedYear", publishedYear,
                                "authorId", authorId
                        ))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asLong();
    }

    @Test
    void getBookById_whenBookDoesNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/books/{id}", 9999L)
                        .with(httpBasic("libraryuser", "librarypass")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with id 9999 not found"));
    }

    @Test
    void createLoan_concurrently_onlyOneLoanIsCreated() throws Exception {
        Long authorId = createAuthor("Concurrent Author");
        Long bookId = createBook(authorId, "Concurrent Book", "69420", 2024);

        int requestCount = 100;

        ExecutorService executor = Executors.newFixedThreadPool(requestCount);
        CountDownLatch readyLatch = new CountDownLatch(requestCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(requestCount);

        AtomicInteger createdCount = new AtomicInteger();
        AtomicInteger badRequestCount = new AtomicInteger();

        try {
            for (int i = 0; i < requestCount; i++) {
                executor.submit(() -> {
                    try {
                        readyLatch.countDown();
                        startLatch.await();

                        int status = mockMvc.perform(post("/api/v1/loans")
                                        .with(httpBasic("libraryuser", "librarypass"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(Map.of("bookId", bookId))))
                                .andReturn()
                                .getResponse()
                                .getStatus();

                        if (status == 201) {
                            createdCount.incrementAndGet();
                        } else if (status == 400) {
                            badRequestCount.incrementAndGet();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            readyLatch.await(5, TimeUnit.SECONDS);
            startLatch.countDown();

            boolean finished = doneLatch.await(15, TimeUnit.SECONDS);

            assertTrue(finished, "All concurrent requests should finish within timeout");
            assertEquals(1, createdCount.get(), "Exactly one loan should be created");
            assertEquals(requestCount - 1, badRequestCount.get(), "All other requests should fail with 400");
            assertEquals(1, loanRepository.count(), "Only one loan row should exist in the database");

        } finally {
            executor.shutdownNow();
        }
    }
}