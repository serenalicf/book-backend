package com.inventory.management.book.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
public @interface DateFormat {
  String message() default "Invalid date format, date format should be yyyy-MM-dd";
  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
