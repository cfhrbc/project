package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserDetails loadUserByUsername(String name);

    List<UserDto> showAllUsers();

    UserDto findUserById(int id);

    UserDto saveUser(UserDto userDto);

    void delete(int id);

    List<UserDto> getUsersWithFilters(Map<String, String> filters, String sortBy, String sortOrder);
}
