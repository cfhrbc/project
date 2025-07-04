package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class WorkRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private TestDataFactory factory;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateWork() {
        var user = factory.createTestUser("john");
        user = userRepository.save(user);

        var work = factory.createWork(user);
        work.getUsers().add(user);
        user.setWork(work);

        var saved = workRepository.save(work);

        assertNotNull(saved.getId());
        assertEquals("Google", saved.getCompany());
        assertEquals("Developer", saved.getPosition());
        assertTrue(saved.getUsers().contains(user));
    }

    @Test
    void testFindWorkById() {
        var user = factory.createTestUser("alice");
        user = userRepository.save(user);

        var work = factory.createWork(user);
        work.getUsers().add(user);
        user.setWork(work);

        var saved = workRepository.save(work);

        var found = workRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Google", found.get().getCompany());
    }

    @Test
    void testUpdateWork() {
        var user = factory.createTestUser("michael");
        user = userRepository.save(user);

        var work = factory.createWork(user);
        work.getUsers().add(user);
        user.setWork(work);

        work = workRepository.save(work);

        work.setCompany("Amazon");
        work.setPosition("Team Lead");

        var updated = workRepository.save(work);

        assertEquals("Amazon", updated.getCompany());
        assertEquals("Team Lead", updated.getPosition());
    }

    @Test
    void testDeleteWork() {
        var user = factory.createTestUser("julia");
        user = userRepository.save(user);

        var work = factory.createWork(user);
        work.getUsers().add(user);
        user.setWork(work);

        work = workRepository.save(work);
        Long id = work.getId();

        workRepository.deleteById(id);

        assertFalse(workRepository.findById(id).isPresent());
    }

    @Test
    void testFindWorkByUserId() {
        var user = factory.createTestUser("emily");
        user = userRepository.save(user);

        var work = factory.createWork(user);
        work.getUsers().add(user);
        user.setWork(work);

        work = workRepository.save(work);

        var found = workRepository.findWorkByUsersId(user.getId());

        assertTrue(found.isPresent());
        assertEquals("Google", found.get().getCompany());
    }
}
