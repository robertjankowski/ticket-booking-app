package com.jankowski.ticketapp.entity.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SurnameValidator implements ConstraintValidator<Surname, String> {

    @Override
    public boolean isValid(String surname, ConstraintValidatorContext context) {
        if (surname.length() < 3)
            return false;
        if (!Character.isUpperCase(surname.charAt(0)))
            return false;
        var twoParts = surname.split("-");
        if (twoParts.length == 2) {
            if (!Character.isUpperCase(twoParts[1].charAt(0)))
                return false;
        }
        return true;
    }
}
