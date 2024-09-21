package com.inventory.management.book.exception;

import com.inventory.management.book.response.ErrorDTO;
import com.inventory.management.book.response.ErrorListDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorListDTO hanldeGeneralException(Exception ex){
        log.error(ex.getMessage(),ex);
        ErrorDTO errorDTO = ErrorDTO.builder()
            .errorMessage("System Busy. Please try again later !")
            .build();

        return ErrorListDTO.builder()
            .errors(Collections.singletonList(errorDTO))
            .build();
    }
    @ExceptionHandler(BookAlreadyExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO hanldeBookExistException(BookAlreadyExistException bEx){
        ErrorDTO errorDTO = ErrorDTO.builder()
            .errorMessage(bEx.getMessage())
            .build();

        return ErrorListDTO.builder()
            .errors(Collections.singletonList(errorDTO))
            .build();
    }

    @ExceptionHandler(CreateBookException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorListDTO handleCreateBookException(CreateBookException cEx){
        ErrorDTO errorDTO = ErrorDTO.builder()
            .errorMessage(cEx.getMessage())
            .build();

        return ErrorListDTO.builder()
            .errors(Collections.singletonList(errorDTO))
            .build();
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorListDTO handleInvalidDateFormatException(InvalidDateFormatException iEx){
        ErrorDTO errorDTO = ErrorDTO.builder()
            .errorMessage(iEx.getMessage())
            .fieldName(iEx.getField())
            .build();

        return ErrorListDTO.builder()
            .errors(Collections.singletonList(errorDTO))
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorListDTO handleValidationException(MethodArgumentNotValidException mEx){
        List<ErrorDTO> errorList = new ArrayList<>();

        for(ObjectError err : mEx.getBindingResult().getAllErrors()){
            String field = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errorList.add(ErrorDTO.builder()
                    .fieldName(field)
                    .errorMessage(errorMessage)
                .build()
            );
        }
        return ErrorListDTO.builder()
            .errors(errorList)
            .build();
    }
}
