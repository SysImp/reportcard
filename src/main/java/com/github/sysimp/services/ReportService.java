package com.github.sysimp.services;

import com.github.sysimp.entities.Employee;
import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.SearchReportModel;
import com.github.sysimp.entities.Specialty;
import com.github.sysimp.repositories.EmployeeRepository;
import com.github.sysimp.repositories.ReportRepository;
import com.github.sysimp.repositories.SpecialtyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReportService {
    private static final Logger LOG = LogManager.getLogger(ReportService.class);

    private SpecialtyRepository specialtyRepository;
    private EmployeeRepository employeeRepository;
    private ReportRepository reportRepository;

    @Autowired
    public ReportService(SpecialtyRepository specialtyRepository,
                         EmployeeRepository employeeRepository, ReportRepository reportRepository) {
        this.specialtyRepository = specialtyRepository;
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
    }

    public boolean isContainsEmployee(Employee employee) {
        return employeeRepository.findAll().contains(employee);
    }

    public boolean isContainsSpecialty(Specialty specialty) {
        return specialtyRepository.findAll().contains(specialty);
    }

    public List<Specialty> getListSpecialties(Sort sort) {
        LOG.debug("getListSpecialties: sort[{}]", sort);
        return specialtyRepository.findAll(sort);
    }

    public List<Employee> getListEmployees(Sort sort) {
        LOG.debug("getListEmployees: sort[{}]", sort);
        return employeeRepository.findAll(sort);
    }

    public void saveReport(Report report) {
        reportRepository.saveAndFlush(report);
    }

    public List<Report> getListReports(Comparator<Report> comparator) {
        List<Report> list = reportRepository.findAll();
        list.sort(comparator);

        return list;
    }

    public List<Report> getListReportsBySearchModel(SearchReportModel searchReportModel, Comparator<Report> comparator) {
        LOG.debug("getListReportsBySearchModel: {}", searchReportModel);
        SearchService searchService = new SearchService(searchReportModel);
        List<Report> list = reportRepository.findAll(searchService.getSpecification());

        list.sort(comparator);
        return list;
    }
}
