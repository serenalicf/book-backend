package com.inventory.management.book.controller;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.enums.CSVHeaderFieldMapping;
import com.inventory.management.book.mapper.BookMapper;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.response.BookDto;
import com.inventory.management.book.response.PageDto;
import com.inventory.management.book.service.BookService;
import com.inventory.management.book.util.DateUtil;
import com.inventory.management.book.util.PaginationUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

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
    public PageDto<BookDto> getBooks(BookSearchCriteria bookSearchCriteria,
        @RequestParam(required = false,defaultValue = "0") final Integer pageNo,
        @RequestParam(required = false,defaultValue = "10") final Integer pageSize,
        @RequestParam(required = false,defaultValue = "entryId,asc") final String[] sort

    ) {
        Page<Book> bookPages = bookService.getBooksByCriteria(bookSearchCriteria, PaginationUtil.getPageable(pageNo, pageSize, sort));
        return BookMapper.INSTANCE.toPagedDto(bookPages);
    }

    @GetMapping(value = "/books/export")
    public void exportToCsv(BookSearchCriteria bookSearchCriteria, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String currentDateTime = DateUtil.getCurrentLocalDateTimeAsString();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=books_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {CSVHeaderFieldMapping.ENTRY_ID.getHeaderName(), CSVHeaderFieldMapping.TITLE.getHeaderName(), CSVHeaderFieldMapping.AUTHOR.getHeaderName(),CSVHeaderFieldMapping.GENRE.getHeaderName(),CSVHeaderFieldMapping.PUBLICATION_DATE.getHeaderName(),CSVHeaderFieldMapping.ISBN.getHeaderName()};
        String[] nameMapping = {CSVHeaderFieldMapping.ENTRY_ID.getFieldName(), CSVHeaderFieldMapping.TITLE.getFieldName(), CSVHeaderFieldMapping.AUTHOR.getFieldName(),CSVHeaderFieldMapping.GENRE.getFieldName(),CSVHeaderFieldMapping.PUBLICATION_DATE.getFieldName(),CSVHeaderFieldMapping.ISBN.getFieldName()};
        List<Book> bookList = bookService.getBooksByCriteria(bookSearchCriteria);

        csvWriter.writeHeader(csvHeader);
        for (Book book : bookList) {
            csvWriter.write(book, nameMapping);
        }

        csvWriter.close();
    }

}
