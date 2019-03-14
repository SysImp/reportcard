package com.github.sysimp.repositories;

import com.github.sysimp.entities.Employee;
import com.github.sysimp.entities.Report;
import com.github.sysimp.entities.Specialty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "classpath:application-test.properties")
public class ReportRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndFind() {
        Employee employee = employeeRepository.getOne(1);
        Specialty specialty = specialtyRepository.getOne(1);
        Report report = new Report(employee, specialty, LocalDate.now(), LocalTime.now(), LocalTime.now(), "description");

        entityManager.persist(report);
        entityManager.flush();

        assertNotNull(reportRepository.getOne(report.getId()));
        assertEquals(report, reportRepository.getOne(report.getId()));
    }

}