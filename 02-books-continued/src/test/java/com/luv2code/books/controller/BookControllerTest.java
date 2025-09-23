// File: src/test/java/com/luv2code/books/controller/BookControllerTest.java
package com.luv2code.books.controller;

import com.luv2code.books.request.BookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBooks_returnsAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void getBooks_withCategoryFilter_returnsFilteredBooks() throws Exception {
        mockMvc.perform(get("/api/books").param("category", "Math"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].category", everyItem(equalToIgnoringCase("Math"))));
    }

    @Test
    void getBookById_validId_returnsBook() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getBookById_invalidId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBookById_negativeId_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/books/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBook_validRequest_returnsCreated() throws Exception {
        BookRequest request = new BookRequest("New Book", "Author", "Science", 4);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createBook_missingFields_returnsBadRequest() throws Exception {
        BookRequest request = new BookRequest("", "", "", 0);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBook_validId_returnsNoContent() throws Exception {
        BookRequest request = new BookRequest("Updated Book", "Author", "Math", 5);
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void updateBook_invalidId_returnsNotFound() throws Exception {
        BookRequest request = new BookRequest("Updated Book", "Author", "Math", 5);
        mockMvc.perform(put("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_validId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_invalidId_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/books/999"))
                .andExpect(status().isNotFound());
    }
}