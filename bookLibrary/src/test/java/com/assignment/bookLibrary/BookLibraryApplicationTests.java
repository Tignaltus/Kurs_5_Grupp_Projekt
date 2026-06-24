package com.assignment.bookLibrary;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.bookLibrary.repository.AuthorRepository;
import com.assignment.bookLibrary.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.cache.type=none",
        "spring.config.import=optional:vault://",
        "spring.data.redis.repositories.enabled=false"
})
class BookLibraryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createAuthor_andGetAuthorById_returnsAuthor() throws Exception {
        Long authorId = createAuthor("J.K. Rowling");

        mockMvc.perform(get("/api/v1/authors/{id}", authorId)
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").value("J.K. Rowling"));
    }

    @Test
    void createBook_andGetBookById_returnsBook() throws Exception {
        Long authorId = createAuthor("J.R.R. Tolkien");
        Long bookId = createBook(authorId, "The Hobbit", "9780261103344", 1937);

        mockMvc.perform(get("/api/v1/books/{id}", bookId)
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("The Hobbit"));
    }

    @Test
    void getAllBooks_returnsOk() throws Exception {
        mockMvc.perform(get("/api/v1/books")
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk());
    }

    private Long createAuthor(String name) throws Exception {
        String response = mockMvc.perform(post("/api/v1/authors")
                        .with(httpBasic("admin", "password"))
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
                        .with(httpBasic("admin", "password"))
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
}