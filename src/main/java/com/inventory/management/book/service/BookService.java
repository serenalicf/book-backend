package com.inventory.management.book.service;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BookService {
    Book createBook(CreateBookRequest createBookRequest);
    Page<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria, Pageable pageable);

    List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria);

    List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria, Sort sort);
}
