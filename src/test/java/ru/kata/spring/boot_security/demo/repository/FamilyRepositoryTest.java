package ru.kata.spring.boot_security.demo.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.Family;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@Transactional
public class FamilyRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateFamily() {
        var user = factory.createTestUser("alice");
        var family = factory.createFamilyMember(user);
        family = familyRepository.save(family);

        assertNotNull(family.getId());
        assertEquals("Brother", family.getRelation());
        assertEquals(user.getId(), family.getUser().getId());
    }

    @Test
    void testFindFamilyById() {
        var user = factory.createTestUser("bob");
        var family = factory.createFamilyMember(user);
        family = familyRepository.save(family);

        var found = familyRepository.findById(family.getId());

        assertTrue(found.isPresent());
        assertEquals("Alex", found.get().getName());
    }

    @Test
    void testUpdateFamily() {
        var user = factory.createTestUser("claire");
        var family = factory.createFamilyMember(user);
        family = familyRepository.save(family);

        family.setAge(35);
        family.setRelation("Sister");
        var updated = familyRepository.save(family);

        assertEquals(35, updated.getAge());
        assertEquals("Sister", updated.getRelation());
    }

    @Test
    void testDeleteFamily() {
        var user = factory.createTestUser("david");
        var family = factory.createFamilyMember(user);
        family = familyRepository.save(family);

        familyRepository.delete(family);
        var found = familyRepository.findById(family.getId());

        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllByUserId() {
        var user = factory.createTestUser("emily");

        var family1 = factory.createFamilyMember(user);
        var family2 = new Family(null, "Father", "John", 60, "9876543210", user);

        familyRepository.save(family1);
        familyRepository.save(family2);

        var result = familyRepository.findAllByUserId(user.getId());

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("Alex")));
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("John")));
    }
}
