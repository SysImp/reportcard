package com.github.sysimp.repositories;

import com.github.sysimp.entities.Specialty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SpecialtyRepositoryTest {
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testSaveAndFind() {
        Specialty specialty = new Specialty();
        specialty.setName("spec");

        entityManager.persist(specialty);
        entityManager.flush();

        assertNotNull(specialtyRepository.getOne(specialty.getId()));
        assertEquals(specialty, specialtyRepository.getOne(specialty.getId()));
    }
}