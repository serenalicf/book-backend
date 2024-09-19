package com.inventory.management.book.request;

import lombok.Data;

@Data
public class BookSearchCriteria {

    private String title;

    private String author;

    private String genre;

    private String fromPublicationDate;

    private String toPublicationDate;

    private String isbn;

}
