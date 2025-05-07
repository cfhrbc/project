package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.House;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Testcontainers
public class HouseRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateHouse() {
        var user = factory.createTestUser("john");
        var house = factory.createHouse(user);
        house = houseRepository.save(house);

        assertNotNull(house.getId());
        assertEquals("123 Main St", house.getAddress());
        assertEquals(85.5, house.getArea());
        assertEquals(user.getId(), house.getUser().getId());
    }

    @Test
    void testFindHouseById() {
        var user = factory.createTestUser("emma");
        var house = factory.createHouse(user);
        house = houseRepository.save(house);

        var found = houseRepository.findById(house.getId());

        assertTrue(found.isPresent());
        assertEquals("123 Main St", found.get().getAddress());
    }

    @Test
    void testUpdateHouse() {
        var user = factory.createTestUser("mike");
        var house = factory.createHouse(user);
        house = houseRepository.save(house);

        house.setAddress("456 Oak Avenue");
        house.setArea(100.0);
        var updated = houseRepository.save(house);

        assertEquals("456 Oak Avenue", updated.getAddress());
        assertEquals(100.0, updated.getArea());
    }

    @Test
    void testDeleteHouse() {
        var user = factory.createTestUser("kate");
        var house = factory.createHouse(user);
        house = houseRepository.save(house);

        houseRepository.delete(house);
        var found = houseRepository.findById(house.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllByUserId() {
        var user = factory.createTestUser("bob");
        var house1 = factory.createHouse(user);
        var house2 = new House(null, "789 Pine Road", 120.0, 2010, user);

        houseRepository.saveAll(List.of(house1, house2));

        var houses = houseRepository.findAllByUserId(user.getId());

        assertEquals(2, houses.size());
        assertTrue(houses.stream().anyMatch(h -> h.getAddress().equals("123 Main St")));
        assertTrue(houses.stream().anyMatch(h -> h.getAddress().equals("789 Pine Road")));
    }
}
