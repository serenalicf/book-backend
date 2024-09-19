package com.inventory.management.book.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookDto {

    private Integer entryId;

    private String title;

    private String author;

    private String genre;

    private String publicationDate;

    private String isbn;
}
