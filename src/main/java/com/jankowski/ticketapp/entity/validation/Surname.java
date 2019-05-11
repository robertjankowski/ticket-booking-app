package com.jankowski.ticketapp.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SurnameValidator.class)
@Documented
public @interface Surname {

    String message() default "{Surname.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
