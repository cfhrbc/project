package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
public class CarRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateCar() {
        var user = factory.createTestUser("john");
        var car = factory.createCar(user, "Toyota", "Camry");
        car = carRepository.save(car);

        assertNotNull(car.getId());
        assertEquals("Toyota", car.getBrand());
        assertEquals(user.getId(), car.getOwner().getId());
    }

    @Test
    void testFindCarById() {
        var user = factory.createTestUser("john");
        var car = factory.createCar(user, "Honda", "Accord");
        car = carRepository.save(car);

        var found = carRepository.findById(car.getId());

        assertTrue(found.isPresent());
        assertEquals("Honda", found.get().getBrand());
    }

    @Test
    void testUpdateCar() {
        var user = factory.createTestUser("anna");
        var car = factory.createCar(user, "Ford", "Focus");
        car = carRepository.save(car);

        car.setColor("Red");
        car.setYear(2020);
        var updated = carRepository.save(car);

        assertEquals("Red", updated.getColor());
        assertEquals(2020, updated.getYear());
    }

    @Test
    void testDeleteCar() {
        var user = factory.createTestUser("mark");
        var car = factory.createCar(user, "BMW", "X5");
        car = carRepository.save(car);

        carRepository.deleteById(car.getId());

        var deleted = carRepository.findById(car.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindAllCars() {
        var user = factory.createTestUser("lisa");
        var car1 = factory.createCar(user, "Mazda", "3");
        var car2 = factory.createCar(user, "Audi", "A4");

        carRepository.saveAll(List.of(car1, car2));

        var cars = carRepository.findAll();
        assertTrue(cars.size() >= 2);
    }

    @Test
    void testFindAllCarsByOwnerId() {
        var user = factory.createTestUser("jack");
        var car1 = factory.createCar(user, "Tesla", "Model 3");
        var car2 = factory.createCar(user, "Nissan", "Leaf");

        carRepository.saveAll(List.of(car1, car2));

        var cars = carRepository.findAllByOwnerId(user.getId());

        assertNotNull(cars);
        assertEquals(2, cars.size());
        assertTrue(cars.stream().allMatch(car -> car.getOwner().getId().equals(user.getId())));
    }
}
