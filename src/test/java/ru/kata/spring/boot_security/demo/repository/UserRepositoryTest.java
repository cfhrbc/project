package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    private Role role;

    @Test
    void testCreateUser() {
        var saved = createUser("jane", "password", "Smith", "jane@example.com", 28);

        assertNotNull(saved.getId());
        assertEquals("jane", saved.getName());
    }

    @Test
    void testUpdateUser() {
        var saved = createUser("emma", "oldpass", "Stone", "emma@example.com", 35);

        saved.setPassword("newpass");
        saved.setAge(36);
        var updated = userRepository.save(saved);

        assertEquals("newpass", updated.getPassword());
        assertEquals(36, updated.getAge());
    }

    @Test
    void testFindById() {
        var saved = createUser("alex", "pass", "Brown", "alex@example.com", 25);

        var found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("alex", found.get().getName());
    }

    @Test
    void testDeleteUser() {
        var saved = createUser("mike", "pass", "Johnson", "mike@example.com", 40);

        userRepository.deleteById(saved.getId());
        var deleted = userRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindByName() {
        createUser("john", "123", "Doe", "john@example.com", 30);

        var found = userRepository.findByName("john");
        assertTrue(found.isPresent());
        assertEquals("john", found.get().getName());
    }

    @BeforeEach
    void setUp() {
        role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
    }

    private User createUser(String name, String password, String surname, String email, int age) {
        var user = new User(name, password, surname, email, age);
        user.setRoles(new HashSet<>(Set.of(role)));
        return userRepository.save(user);
    }

    @Test
    void testGetUsersWithSortingByAgeAsc() {

        createUser("alice", "pass1", "White", "alice@example.com", 30);
        createUser("bob", "pass2", "Black", "bob@example.com", 25);
        createUser("charlie", "pass3", "Green", "charlie@example.com", 35);

        Map<String, String> filters = new HashMap<>();
        List<UserDto> sortedUsers = userService.getUsersWithFilters(filters, "age", "asc");

        assertEquals(4, sortedUsers.size());
        assertEquals("bob", sortedUsers.get(0).getName());
        assertEquals("admin", sortedUsers.get(1).getName());
        assertEquals("alice", sortedUsers.get(2).getName());
        assertEquals("charlie", sortedUsers.get(3).getName());
    }
}
