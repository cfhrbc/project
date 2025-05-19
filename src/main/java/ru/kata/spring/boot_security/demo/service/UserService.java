package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    UserDetails loadUserByUsername(String name);

    List<UserDto> showAllUsers();

    UserDto findUserById(Long id);

    Optional<User> findUserEntityById(Long id);

    UserDto saveUser(UserDto userDto);

    void saveUserWork(User user);

    void delete(Long id);

    List<UserDto> getUsersWithFilters(Map<String, String> filters, String sortBy, String sortOrder);
}
