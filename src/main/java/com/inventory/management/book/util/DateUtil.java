package com.inventory.management.book.util;

import com.inventory.management.book.exception.InvalidDateFormatException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate convertStringToLocalDate(String dateString){
        try {
             return LocalDate.parse(dateString);
        } catch (Exception ex){
            throw new InvalidDateFormatException(dateString);
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
