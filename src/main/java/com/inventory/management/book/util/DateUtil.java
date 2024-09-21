package com.inventory.management.book.util;

import com.inventory.management.book.exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
    public static LocalDate convertStringToLocalDate(String field,String dateString){
        try {
             return LocalDate.parse(dateString);
        } catch (Exception ex){
            log.error(ex.getMessage(),ex);
            throw new InvalidDateFormatException(field,dateString);
        }
    }

    public static String convertLocalDateToString(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getCurrentLocalDateTimeAsString(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
