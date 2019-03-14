package com.github.sysimp.entities.validator;

import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public abstract class BaseValidator implements Validator {
    LocalDate parseDate(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
        return localDate;
    }

    LocalTime parseTime(String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
        return localTime;
    }
}
