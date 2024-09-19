package com.inventory.management.book.service;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.request.PagingRequest;
import com.inventory.management.book.response.CsvOutputResponse;

import java.util.List;

public interface BookService {
    Book createBook(CreateBookRequest createBookRequest);

    List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria, PagingRequest pagingRequest);

    List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria);

    CsvOutputResponse getBooksInCsvByCriteria(BookSearchCriteria bookSearchCriteria);

}
