package com.github.sysimp.services;

import com.github.sysimp.entities.Employee;
import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.entities.Specialty;
import com.github.sysimp.repositories.EmployeeRepository;
import com.github.sysimp.repositories.ReportRepository;
import com.github.sysimp.repositories.SpecialtyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SearchServiceTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private ReportRepository reportRepository;
    private SearchReportModel searchReportModel;

    @Before
    public void init() {
        searchReportModel = new SearchReportModel();
    }

    @Test
    public void testEmployeeCriteria() {
        emptySearchModel();
        Employee searchEmployee = employeeRepository.getOne(1);
        searchReportModel.setEmployee(searchEmployee);

        List<Report> result = getListSearchResult(searchReportModel);
        for (Report report : result) {
            assertNotEquals(report.getEmployee(), searchEmployee);
        }
    }

    @Test
    public void testSpecialtyCriteria() {
        emptySearchModel();
        Specialty searchSpecialty = specialtyRepository.getOne(1);
        searchReportModel.setSpecialty(searchSpecialty);

        List<Report> result = getListSearchResult(searchReportModel);
        for (Report report : result) {
            assertNotEquals(report.getEmployee(), searchSpecialty);
        }
    }

    @Test
    public void testDescriptionCriteria() {
        emptySearchModel();
        String description = "у";
        searchReportModel.setDescription(description);

        List<Report> result = getListSearchResult(searchReportModel);
        for (Report report : result) {
            assertTrue(report.getDescription().contains(description));
        }
    }

    @Test
    public void testTimeCriteria() {
        emptySearchModel();
        String timeStart = "09:00";
        String timeEnd = "12:00";
        searchReportModel.setLocalTimeStart(timeStart);
        searchReportModel.setLocalTimeEnd(timeEnd);

        LocalTime localTimeStart = LocalTime.parse(timeStart);
        LocalTime localTimeEnd = LocalTime.parse(timeEnd);

        List<Report> result = getListSearchResult(searchReportModel);
        for (Report report : result) {
            assertTrue(report.getLocalTimeStart().compareTo(localTimeStart) >= 0);
            assertTrue(report.getLocalTimeEnd().compareTo(localTimeEnd) <= 0);
        }
    }

    @Test
    public void testDateCriteria() {
        emptySearchModel();
        String dateStart = "2019-03-05";
        String dateEnd = "2019-03-09";
        searchReportModel.setLocalDateStart(dateStart);
        searchReportModel.setLocalDateEnd(dateEnd);

        LocalDate localDateStart = LocalDate.parse(dateStart);
        LocalDate localDateEnd = LocalDate.parse(dateEnd);

        List<Report> result = getListSearchResult(searchReportModel);
        for (Report report : result) {
            assertTrue(report.getLocalDate().compareTo(localDateStart) >= 0);
            assertTrue(report.getLocalDate().compareTo(localDateEnd) <= 0);
        }
    }

    private void emptySearchModel() {
        searchReportModel.setEmployee(null);
        searchReportModel.setSpecialty(null);
        searchReportModel.setLocalTimeStart("");
        searchReportModel.setLocalTimeEnd("");
        searchReportModel.setLocalDateStart("");
        searchReportModel.setLocalDateEnd("");
        searchReportModel.setDescription("");
    }

    private List<Report> getListSearchResult(SearchReportModel searchReportModel) {
        SearchService searchService = new SearchService(searchReportModel);
        return reportRepository.findAll(searchService.getSpecification());
    }
}