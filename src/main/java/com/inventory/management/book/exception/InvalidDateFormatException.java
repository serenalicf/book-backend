package com.inventory.management.book.exception;

public class InvalidDateFormatException extends RuntimeException{
    public InvalidDateFormatException(String dateString){
        super("Invalid date format: "+ dateString + ". Expected YYYY-MM-DD.");
    }
}
