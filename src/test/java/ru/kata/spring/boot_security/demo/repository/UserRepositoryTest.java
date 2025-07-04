package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private TestDataFactory factory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testLoadUserByUsername() {
        var user = factory.createTestUser("alex");
        userRepository.save(user);

        var loadedUser = userService.loadUserByUsername("alex");

        assertNotNull(loadedUser);
        assertEquals("alex", loadedUser.getUsername());
    }

    @Test
    void testShowAllUsers() {
        var user1 = factory.createTestUser("user1");
        var user2 = factory.createTestUser("user2");
        userRepository.saveAll(List.of(user1, user2));

        var users = userService.showAllUsers();

        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("user1")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("user2")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("admin")));

    }

    @Test
    void testDeleteUser() {
        var user = factory.createTestUser("del");
        user = userRepository.save(user);

        userService.delete(user.getId());

        assertTrue(userRepository.findById(user.getId()).isEmpty());
    }

    @Test
    void testSaveUser() {
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER", new HashSet<>())));
        var userDto = factory.createTestUserDto("savedUser", Set.of(role.getName()));

        var savedDto = userService.saveUser(userDto);

        var saved = userRepository.findById(savedDto.getId());
        assertTrue(saved.isPresent());
        assertEquals("savedUser", saved.get().getName());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), saved.get().getPassword()));
    }

    @Test
    void testFindUserById() {
        var user = factory.createTestUser("byId");
        user = userRepository.save(user);

        var dto = userService.findUserById(user.getId());

        assertEquals("byId", dto.getName());
    }

    @Test
    void testSaveUserWork() {
        var user = factory.createTestUser("worker");
        userService.saveUserWork(user);

        assertNotNull(user.getId());
        assertTrue(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void testFindUserEntityById() {
        var user = factory.createTestUser("entity");
        user = userRepository.save(user);

        var found = userService.findUserEntityById(user.getId());

        assertTrue(found.isPresent());
        assertEquals("entity", found.get().getName());
    }

    @Test
    void testGetUsersWithFilters() {
        var user1 = factory.createTestUser("john");
        user1.setAge(30);
        var user2 = factory.createTestUser("jane");
        user2.setAge(25);
        userRepository.saveAll(List.of(user1, user2));

        var filters = Map.of("age", "30");

        var result = userService.getUsersWithFilters(filters, "name", "asc");

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getName());
    }
}
