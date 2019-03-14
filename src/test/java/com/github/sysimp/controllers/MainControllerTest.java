package com.github.sysimp.controllers;

import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.repositories.EmployeeRepository;
import com.github.sysimp.repositories.ReportRepository;
import com.github.sysimp.repositories.SpecialtyRepository;
import com.github.sysimp.services.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    private static final String SEARCH_URI = "/search?employee=%s&specialty=%s&localDateStart=%s" +
            "&localDateEnd=%s&localTimeStart=%s&localTimeEnd=%s&description=%s";
    private static final String CREATE_URI = "/create?employee=%s&specialty=%s&strDate=%s&strTimeStart=%s" +
            "&strTimeEnd=%s&description=%s";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testIndexPage() throws Exception{
        mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchReport() throws Exception{
        SearchReportModel searchReportModel = getTestSearchModel();
        SearchService searchService = new SearchService(searchReportModel);
        List<Report> result = reportRepository.findAll(searchService.getSpecification());
        //(ID: 1, 'Попал в ДТП', '2019-03-10', '09:00', '12:00', EMPLOYEE: 1, SPECIALTY: 5) == 1
        if (result.size() != 1)
            fail("Bad specification!");

        Report report = result.get(0);
        MvcResult mvcResult = mockMvc.perform(post(getSearchReportPerform(searchReportModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("reports", hasSize(1)))
                .andReturn();

        String resultSearch = Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reports").toString();
        assertTrue(resultSearch.contains(String.format("Report{id=%s", report.getId().toString())));
    }

    @Test
    public void testCreateReport() throws Exception{
        Report reportCreate = getTestCreateReport();
        MvcResult mvcResult = mockMvc.perform(post(getCreateReportPerform(reportCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("reports", hasSize(10)))
                .andReturn();

        String resultSearch = Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reports").toString();
        assertEquals(10, reportRepository.findAll().size());
        assertTrue(resultSearch.contains("Report{id=10"));
    }

    private Report getTestCreateReport() {
        Report report = new Report();
        report.setEmployee(employeeRepository.getOne(1));
        report.setSpecialty(specialtyRepository.getOne(1));
        report.setStrDate("2019-10-10");
        report.setStrTimeStart("10:10");
        report.setStrTimeEnd("20:20");
        report.setDescription("TESTDES");

        return report;
    }


    private SearchReportModel getTestSearchModel() {
        SearchReportModel searchReportModel = new SearchReportModel();
        //(ID: 1, 'Попал в ДТП', '2019-03-10', '09:00', '12:00', EMPLOYEE: 1, SPECIALTY: 5),
        searchReportModel.setEmployee(employeeRepository.getOne(1));
        searchReportModel.setSpecialty(specialtyRepository.getOne(5));
        searchReportModel.setDescription("Попал в ДТП");
        searchReportModel.setLocalDateStart("2019-03-10");
        searchReportModel.setLocalDateEnd("2019-03-10");
        searchReportModel.setLocalTimeStart("09:00");
        searchReportModel.setLocalTimeEnd("12:00");

        return searchReportModel;
    }

    private String getSearchReportPerform(SearchReportModel searchReportModel) {
        return String.format(SEARCH_URI, searchReportModel.getEmployee().getId(), searchReportModel.getSpecialty().getId(), searchReportModel.getLocalDateStart(),
                searchReportModel.getLocalDateEnd(), searchReportModel.getLocalTimeStart(), searchReportModel.getLocalTimeEnd(), searchReportModel.getDescription());
    }

    private String getCreateReportPerform(Report report) {
        return String.format(CREATE_URI, report.getEmployee().getId(),
                report.getSpecialty().getId(), report.getStrDate(), report.getStrTimeStart(), report.getStrTimeEnd(), report.getDescription());
    }

}