package com.github.sysimp.entities.validator;

import com.github.sysimp.entities.Employee;
import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.Specialty;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.Assert.*;

public class ReportValidatorTest {
    private ReportValidator reportValidator;

    private Employee employee;
    private Specialty specialty;
    private Report report;

    @Before
    public void init() {
        reportValidator = new ReportValidator();

        employee = new Employee();
        specialty = new Specialty();
        report = new Report();
    }

    @Test
    public void testSupports() {
        assertFalse(reportValidator.supports(Class.class));
        assertFalse(reportValidator.supports(BaseValidator.class));
        assertFalse(reportValidator.supports(SearchValidator.class));

        assertTrue(reportValidator.supports(report.getClass()));
    }

    @Test
    public void testAllParamsIsNull() {
        emptyReport();
        BindException bindException = getNewBindException();

        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("employee")).getCode(), "validator.report.employee.isNull");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("specialty")).getCode(), "validator.report.specialty.isNull");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("strDate")).getCode(), "validator.report.date.isNull");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeStart")).getCode(), "validator.report.startTime.isNull");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeEnd")).getCode(), "validator.report.endTime.isNull");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    @Test
    public void testFieldStrDateInvalid() {
        fillReport();
        report.setStrDate("fail");

        BindException bindException = getNewBindException();
        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("strDate")).getCode(), "validator.report.date.isBad");
        } catch (NullPointerException e) {
            fail("Expected field is Null!");
        }
    }

    @Test
    public void testFieldsTimeInvalid() {
        fillReport();
        report.setStrTimeStart("fail");
        report.setStrTimeEnd("fail");

        BindException bindException = getNewBindException();
        try {
        assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeStart")).getCode(), "validator.report.time.isBad");
        assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeEnd")).getCode(), "validator.report.time.isBad");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    @Test
    public void testFieldsTimeGreaterAndLess() {
        fillReport();
        report.setStrTimeStart("20:20");
        report.setStrTimeEnd("10:10");

        BindException bindException = getNewBindException();
        try {
        assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeStart")).getCode(), "validator.report.startTime.isGreat");
        assertEquals(Objects.requireNonNull(bindException.getFieldError("strTimeEnd")).getCode(), "validator.report.endTime.isSmall");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    private BindException getNewBindException() {
        BindException bindException = new BindException(report, "report");
        ValidationUtils.invokeValidator(reportValidator, report, bindException);

        return bindException;
    }

    private void emptyReport() {
        report.setEmployee(null);
        report.setSpecialty(null);

        report.setStrDate("");
        report.setStrTimeEnd("");
        report.setStrTimeStart("");
    }

    private void fillReport() {
        report.setEmployee(employee);
        report.setSpecialty(specialty);

        LocalDate localDate = LocalDate.now();
        LocalTime localTimeStart = LocalTime.now();
        LocalTime localTimeEnd = LocalTime.now();

        report.setStrDate(localDate.toString());
        report.setStrTimeStart(localTimeStart.toString());
        report.setStrTimeEnd(localTimeEnd.toString());

        report.setLocalDate(localDate);
        report.setLocalTimeStart(localTimeStart);
        report.setLocalTimeEnd(localTimeEnd);
    }

}