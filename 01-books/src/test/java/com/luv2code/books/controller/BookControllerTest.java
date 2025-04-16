package com.luv2code.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.books.entity.Book;
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
    void shouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(6))))
                .andExpect(jsonPath("$[0].title", is("Title one")));
    }

    @Test
    void shouldReturnBooksByCategory() throws Exception {
        mockMvc.perform(get("/api/books")
                        .param("category", "math"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].category", is("math")));
    }

    @Test
    void shouldReturnBookByTitle() throws Exception {
        mockMvc.perform(get("/api/books/Title one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title one")))
                .andExpect(jsonPath("$.author",
                        anyOf(equalTo("Author one"), equalTo("Updated Author"))));
    }

    @Test
    void shouldCreateNewBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "fiction");

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/New Title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Title")));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book updatedBook = new Book("Title one", "Updated Author", "science");

        mockMvc.perform(put("/api/books/Title one")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/Title one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is("Updated Author")));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/Title one"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/Title one"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}