package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    User findUserById(int id);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUser(int id);
    User findByUsername(String username);
}



/*
public interface UserService {
    List<User> showAllUser();

    User getUserById(int id);

    void save(User user);

    void update(User user);

    void delete(int id);
}

 */
