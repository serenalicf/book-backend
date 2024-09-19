package com.inventory.management.book.controller;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.mapper.BookMapper;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.request.PagingRequest;
import com.inventory.management.book.response.BookDto;
import com.inventory.management.book.response.CsvOutputResponse;
import com.inventory.management.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public BookDto createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book book = bookService.createBook(createBookRequest);
        return BookMapper.INSTANCE.toDto(book);
    }

    @GetMapping("/books")
    public List<BookDto> getBooks(BookSearchCriteria bookSearchCriteria, PagingRequest pagingRequest) {
        List<Book> bookList = bookService.getBooksByCriteria(bookSearchCriteria, pagingRequest);
        return BookMapper.INSTANCE.toDtoList(bookList);
    }

    @GetMapping(value = "/books/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToCsv(BookSearchCriteria bookSearchCriteria) throws IOException {
        CsvOutputResponse csvOutputResponse = bookService.getBooksInCsvByCriteria(bookSearchCriteria);

        // Set up the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+csvOutputResponse.getFileName());

        // Return the response entity
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvOutputResponse.getCsvByteArray());
    }

}
