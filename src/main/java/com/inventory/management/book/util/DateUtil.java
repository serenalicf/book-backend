package com.inventory.management.book.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
    public static LocalDate convertStringToLocalDate(String dateString,String pattern){
        try {
            return LocalDate.parse(dateString,DateTimeFormatter.ofPattern(pattern));
        } catch (Exception ex){
            log.error(ex.getMessage(),ex);
            throw ex;
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
