package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.Education;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@SpringBootTest
@Testcontainers
@Transactional
public class EducationRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateEducation() {
        var user = factory.createTestUser("john");
        var education = factory.createEducation(user);
        education = educationRepository.save(education);

        assertNotNull(education.getId());
        assertEquals("University", education.getInstitution());
        assertEquals(user.getId(), education.getUser().getId());
    }

    @Test
    void testFindEducationById() {
        var user = factory.createTestUser("anna");
        var education = factory.createEducation(user);
        education = educationRepository.save(education);

        var found = educationRepository.findById(education.getId());

        assertTrue(found.isPresent());
        assertEquals("Bachelor", found.get().getDegree());
    }

    @Test
    void testUpdateEducation() {
        var user = factory.createTestUser("mike");
        var education = factory.createEducation(user);
        education = educationRepository.save(education);

        education.setDegree("Master");
        education.setEndYear(2016);
        var updated = educationRepository.save(education);

        assertEquals("Master", updated.getDegree());
        assertEquals(2016, updated.getEndYear());
    }

    @Test
    void testDeleteEducation() {
        var user = factory.createTestUser("kate");
        var education = factory.createEducation(user);
        education = educationRepository.save(education);

        educationRepository.delete(education);
        var found = educationRepository.findById(education.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllByUserId() {
        var user = factory.createTestUser("bob");
        var edu1 = factory.createEducation(user);
        var edu2 = new Education(null, "College", "Associate", 2005, 2008, user);

        educationRepository.saveAll(List.of(edu1, edu2));

        var list = educationRepository.findAllByUserId(user.getId());

        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(e -> e.getInstitution().equals("University")));
        assertTrue(list.stream().anyMatch(e -> e.getInstitution().equals("College")));
    }
}

