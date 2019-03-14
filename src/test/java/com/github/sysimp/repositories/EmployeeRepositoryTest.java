package com.github.sysimp.repositories;

import com.github.sysimp.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndFind() {
        Employee employee = new Employee();
        employee.setName("spec");

        entityManager.persist(employee);
        entityManager.flush();

        assertNotNull(employeeRepository.getOne(employee.getId()));
        assertEquals(employee, employeeRepository.getOne(employee.getId()));
    }
}