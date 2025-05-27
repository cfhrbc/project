package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TestDataFactory {

    @Autowired
    private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    public User createTestUser(String name) {
        var role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        var user = new User(name, "pass", "Test", name + "@mail.com", 30);
        user.setRoles(new HashSet<>(Set.of(role)));
        return userRepository.save(user);
    }

    public UserDto createTestUserDto(String name, Set<String> roleNames) {
        Set<RoleDto> roles = roleNames.stream()
                .map(roleName -> new RoleDto(null, roleName))
                .collect(Collectors.toSet());

        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setPassword("password123");
        userDto.setSurname("TestSurname");
        userDto.setEmail(name + "@mail.com");
        userDto.setAge(25);
        userDto.setRoles(roles);

        return userDto;
    }

    public Car createCar(User owner, String brand, String model) {
        var car = new Car(null, brand, model, "Black", 2020, owner);
        return car;
    }

    public Education createEducation(User user) {
        Set<User> users = new HashSet<>();
        users.add(user);
        return new Education(null, "University", "Bachelor", 2010, 2014, users);
    }

    public Family createFamilyMember(User user) {
        return new Family(null, "Brother", "Alex", 30, "1234567890", user);
    }

    public House createHouse(User user) {
        return new House(null, "123 Main St", 85.5, 2005, user);
    }

    public SocialMedia createSocialMedia(User user) {
        return new SocialMedia(null, "Twitter", "john_doe", "https://twitter.com/john_doe", user);
    }

    public Work createWork(User user) {
        var work = new Work();
        work.setCompany("Google");
        work.setPosition("Developer");
        work.setStartDate("2020-01-01");
        work.setEndDate("2023-01-01");
        return work;
    }
}
