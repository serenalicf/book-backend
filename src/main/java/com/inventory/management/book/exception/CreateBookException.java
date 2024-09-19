package com.inventory.management.book.exception;

public class CreateBookException extends RuntimeException {
    public CreateBookException(String isbn){
        super("Fail to create book with isbn: " + isbn);
    }
}
