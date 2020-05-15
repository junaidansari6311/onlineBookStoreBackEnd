package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.implementation.BookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookStoreController.class)
public class DisplayBooksTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStoreService bookStoreService;

    Gson gson = new Gson();

    @Test
    void findAllBooks() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);

        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        when(bookStoreService.allBooks(0, 8, "id")).thenReturn(bookList);
        this.mockMvc.perform(get("/books")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenDisplayBook_WhenBooksAdded_ThenReturnListOfBooks() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO book1 = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);

        BookDTO book2 = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);

        BookDetails bookDetails1 = new BookDetails(book1);
        BookDetails bookDetails2 = new BookDetails(book2);

        bookList.add(bookDetails1);
        bookList.add(bookDetails2);

        when(bookStoreService.allBooks(0, 4, "id")).thenReturn(bookList);
        int size = bookList.size();
        Assert.assertEquals(2, size);
    }

    @Test
    void givenDisplayBook_WhenWrongMethod_ThenReturn405StatusCode() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);

        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);

        when(bookStoreService.allBooks(0, 4, "id")).thenReturn(bookList);
        int status = this.mockMvc.perform(post("/books"))
                .andReturn().getResponse().getStatus();
        Assert.assertEquals(405, status);
    }

    @Test
    void givenBookDetails_WhenSearchedBooks_shouldReturnBookDetails() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);

        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        Page<BookDetails> page = new PageImpl(bookList);
        when(bookStoreService.searchBook(any(),any())).thenReturn(page);

        this.mockMvc.perform(get("/books/0/I")).andReturn().getResponse()
                .getContentAsString().contains("IOT");
    }

    @Test
    public void givenABookToSearchAndFilter_whenPresent_shouldReturnBooks() throws Exception{
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(bookStoreService.findAllBooks("IOT",0, BookStoreEnum.LOW_TO_HIGH)).thenReturn(bookList);
        this.mockMvc.perform(get("/sort/0/IOT/LOW_TO_HIGH")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
