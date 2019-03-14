package com.github.sysimp.entities.validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BaseValidatorTest {
    private BaseValidator baseValidator;
    private String[] failDate;
    private String[] successfulDate;
    private String[] failTime;
    private String[] successfulTime;

    @Before
    public void init() {
        baseValidator = new BaseValidator() {
            @Override
            public boolean supports(Class<?> aClass) {
                return false;
            }

            @Override
            public void validate(Object o, Errors errors) {

            }
        };
        successfulDate = new String[] {"2019-01-01", "2000-02-28", "2020-02-29"};
        failDate = new String[] {"2019:10:10", "2019-10-1", "2019-10", "10-10:2019", "2019-02-30"};

        successfulTime = new String[] {"00:00", "01:59", "23:59"};
        failTime = new String[] {"24:00", "20:60", "40:10", "-10:10", "10-10", "1010:10", "10*10"};
    }

    @Test
    public void parseDate() {
        for (String date : failDate) {
            assertNull(date, baseValidator.parseDate(date));
        }
        for (String date : successfulDate) {
            assertNotNull(date, baseValidator.parseDate(date));
        }
    }

    @Test
    public void parseTime() {
        for (String time : failTime) {
            assertNull(time, baseValidator.parseTime(time));
        }
        for (String time : successfulTime) {
            assertNotNull(time, baseValidator.parseTime(time));
        }
    }
}