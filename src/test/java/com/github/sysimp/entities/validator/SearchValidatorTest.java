package com.github.sysimp.entities.validator;

import com.github.sysimp.entities.Employee;
import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.entities.Specialty;
import com.github.sysimp.services.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchValidatorTest {
    @Mock
    private ReportService reportService;

    private SearchValidator searchValidator;
    private SearchReportModel searchReportModel;
    private Employee employee;
    private Specialty specialty;

    @Before
    public void init() {
        searchValidator = new SearchValidator(reportService);
        searchReportModel = new SearchReportModel();
        employee = new Employee(1, "Emp");
        specialty = new Specialty(1, "Spc");

        when(reportService.isContainsEmployee(employee)).thenReturn(true);
        when(reportService.isContainsSpecialty(specialty)).thenReturn(true);
    }

    @Test
    public void testSupports() {
        assertFalse(searchValidator.supports(Class.class));
        assertFalse(searchValidator.supports(BaseValidator.class));
        assertFalse(searchValidator.supports(ReportValidator.class));

        assertTrue(searchValidator.supports(searchReportModel.getClass()));
    }

    @Test
    public void testIsFakeEmployee() {
        fillSearchModel();
        searchReportModel.setEmployee(new Employee());

        BindException bindException = getNewBindException();
        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("employee")).getCode(), "validator.search.employee.isFake");
        } catch (NullPointerException e) {
            fail("Expected field is Null!");
        }
    }

    @Test
    public void testIsFakeSpecialty() {
        fillSearchModel();
        searchReportModel.setSpecialty(new Specialty());

        BindException bindException = getNewBindException();
        try {
        assertEquals(Objects.requireNonNull(bindException.getFieldError("specialty")).getCode(), "validator.search.specialty.isFake");
        } catch (NullPointerException e) {
            fail("Expected field is Null!");
        }
    }

    @Test
    public void testFieldsTimeInvalid() {
        fillSearchModel();
        searchReportModel.setLocalTimeStart("fail");
        searchReportModel.setLocalTimeEnd("fail");

        BindException bindException = getNewBindException();
        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localTimeStart")).getCode(), "validator.report.time.isBad");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localTimeEnd")).getCode(), "validator.report.time.isBad");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    @Test
    public void testFieldsDateInvalid() {
        fillSearchModel();
        searchReportModel.setLocalDateStart("fail");
        searchReportModel.setLocalDateEnd("fail");

        BindException bindException = getNewBindException();
        try {
        assertEquals(Objects.requireNonNull(bindException.getFieldError("localDateStart")).getCode(), "validator.report.date.isBad");
        assertEquals(Objects.requireNonNull(bindException.getFieldError("localDateEnd")).getCode(), "validator.report.date.isBad");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    @Test
    public void testFieldsTimeGreaterAndLess() {
        fillSearchModel();
        searchReportModel.setLocalTimeStart("20:20");
        searchReportModel.setLocalTimeEnd("10:10");

        BindException bindException = getNewBindException();
        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localTimeStart")).getCode(), "validator.report.startTime.isGreat");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localTimeEnd")).getCode(), "validator.report.endTime.isSmall");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    @Test
    public void testFieldsDateGreaterAndLess() {
        fillSearchModel();
        searchReportModel.setLocalDateStart("2019-01-01");
        searchReportModel.setLocalDateEnd("2018-01-01");

        BindException bindException = getNewBindException();
        try {
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localDateStart")).getCode(), "validator.search.startDate.isGreat");
            assertEquals(Objects.requireNonNull(bindException.getFieldError("localDateEnd")).getCode(), "validator.search.endDate.isSmall");
        } catch (NullPointerException e) {
            fail("Expected fields is Null!");
        }
    }

    private BindException getNewBindException() {
        BindException bindException = new BindException(searchReportModel, "searchReportModelTest");
        ValidationUtils.invokeValidator(searchValidator, searchReportModel, bindException);

        return bindException;
    }

    private void fillSearchModel() {
        searchReportModel.setEmployee(employee);
        searchReportModel.setSpecialty(specialty);

        searchReportModel.setLocalDateStart("2019-01-01");
        searchReportModel.setLocalDateEnd("2020-01-01");

        searchReportModel.setLocalTimeStart("00:00");
        searchReportModel.setLocalTimeEnd("23:59");
    }

}