package com.inventory.management.book.exception;

public class InvalidDateFormatException extends RuntimeException{
    private String field;
    public InvalidDateFormatException(String field,String dateString){
        super("Invalid date format: "+ dateString + ". Expected YYYY-MM-DD.");
        this.field=field;
    }

    public String getField() {
        return field;
    }
}
