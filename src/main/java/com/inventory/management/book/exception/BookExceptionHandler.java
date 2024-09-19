package com.inventory.management.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookAlreadyExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String hanldeBookExistException(BookAlreadyExistException bEx){
        return bEx.getMessage();
    }

    @ExceptionHandler(CreateBookException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleCreateBookException(CreateBookException cEx){
        return cEx.getMessage();
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDateFormatException(InvalidDateFormatException iEx){
        return iEx.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException mEx){
        Map<String, String> errorMap = new HashMap<>();

        for(ObjectError err : mEx.getBindingResult().getAllErrors()){
            String field = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errorMap.put(field, errorMessage);
        }
        return errorMap;
    }
}
