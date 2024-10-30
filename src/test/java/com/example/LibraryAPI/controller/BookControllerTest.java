package com.example.LibraryAPI.controller;

import com.example.LibraryAPI.entity.Author;
import com.example.LibraryAPI.entity.Book;
import com.example.LibraryAPI.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;
    private Author author;


    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1);
        author.setName("Author");

        book = new Book();
        book.setId(1);
        book.setAuthor(author);
        book.setTitle("Book");
    }

    @Test
    public void shouldReturn_AllBooks_WhenPaged() throws Exception {
        List<Book> books = List.of(book);
        Page<Book> page = new PageImpl<>(books, PageRequest.of(0, 3), 1);

        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/books?page=0&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Book"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(3));
    }

    @Test
    public void shouldReturn_BookCurrentId_WhenExists() throws Exception {
        when(bookService.getBookById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book"));
    }

    @Test
    public void shouldCreate_Book_WhenValidRequest() throws Exception {
        when(bookService.addBook(any())).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Book"));
    }

    @Test
    public void shouldUpdate_Book_WhenValidIdAndRequest() throws Exception {
        when(bookService.updateBook(anyInt(), any())).thenReturn(book);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book"));
    }

    @Test
    public void shouldDelete_Bool_WhenExists() throws Exception {
        when(bookService.deleteBook(1)).thenReturn(true);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}
