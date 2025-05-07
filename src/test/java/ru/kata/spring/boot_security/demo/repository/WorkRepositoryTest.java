package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.Work;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class WorkRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateWork() {
        var user = factory.createTestUser("john");
        var work = factory.createWork(user);
        work = workRepository.save(work);

        assertNotNull(work.getId());
        assertEquals("Company Inc", work.getCompany());
        assertEquals("Engineer", work.getPosition());
        assertEquals(user.getId(), work.getUser().getId());
    }

    @Test
    void testFindWorkById() {
        var user = factory.createTestUser("emma");
        var work = factory.createWork(user);
        work = workRepository.save(work);

        var found = workRepository.findById(work.getId());

        assertTrue(found.isPresent());
        assertEquals("Company Inc", found.get().getCompany());
        assertEquals("Engineer", found.get().getPosition());
    }

    @Test
    void testUpdateWork() {
        var user = factory.createTestUser("david");
        var work = factory.createWork(user);
        work = workRepository.save(work);

        work.setCompany("New Corp");
        work.setPosition("Manager");
        var updated = workRepository.save(work);

        assertEquals("New Corp", updated.getCompany());
        assertEquals("Manager", updated.getPosition());
    }

    @Test
    void testDeleteWork() {
        var user = factory.createTestUser("lisa");
        var work = factory.createWork(user);
        work = workRepository.save(work);

        workRepository.delete(work);
        var found = workRepository.findById(work.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllByUserId() {
        var user = factory.createTestUser("mike");

        var work1 = factory.createWork(user);
        var work2 = new Work(null, "Tech Solutions", "Analyst", "2018-06-01", "2021-01-01", user);
        workRepository.saveAll(List.of(work1, work2));

        var result = workRepository.findAllByUserId(user.getId());

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(w -> w.getCompany().equals("Company Inc")));
        assertTrue(result.stream().anyMatch(w -> w.getCompany().equals("Tech Solutions")));
    }
}
