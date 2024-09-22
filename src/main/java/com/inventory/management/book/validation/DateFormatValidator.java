package com.inventory.management.book.validation;

import com.inventory.management.book.util.DateUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat,String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    try{
      DateUtil.convertStringToLocalDate(s,"yyyy-MM-dd");
      return true;
    }catch (Exception e){
      return false;
    }
  }
}
