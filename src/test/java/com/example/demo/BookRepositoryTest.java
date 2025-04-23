package com.example.demo;

import com.example.demo.domain.enumeration.BookStatus;
import com.example.demo.service.dto.BookDTO;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/** Integration tests for {@link com.example.demo.controller.api.BookRestController}*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BookRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        BookDTO bookDTO = BookDTO.builder()
                .title("Test Book")
                .author("John Doe")
                .genre("Fiction")
                .language("English")
                .bookStatus(BookStatus.EXCHANGE)
                .condition("New")
                .build();

        bookService.save(bookDTO);
    }

    @AfterEach
    void tearDown() {
        bookService.deleteById(1L);
    }

    @Test
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/ukr-lit-exchange/books/1"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.genre").value("Fiction"))
                .andExpect(jsonPath("$.language").value("English"))
                .andExpect(jsonPath("$.bookStatus").value("EXCHANGE"))
                .andExpect(jsonPath("$.condition").value("New"));
    }
}
