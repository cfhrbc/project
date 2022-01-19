package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    final UserDao userDao;
    final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public List<User> showAllUser() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        Optional<User> user = userDao.findById(id);
        return user.orElse(new User());
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    @Override


    public void update(User user) {
        User savedUser = userDao.findById(user.getId()).orElseThrow(() ->
                new RuntimeException("User not found with id: " + user.getId()));

        // Обновляем поля пользователя
        savedUser.setName(user.getName());
        savedUser.setSurname(user.getSurname());
        savedUser.setEmail(user.getEmail());
        savedUser.setAge(user.getAge());

        // Обновляем пароль только если он был изменен
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        // Обновляем роли
        savedUser.getRoles().addAll(user.getRoles());

        // Сохраняем пользователя
        userDao.save(savedUser);
    }


    @Transactional
    @Override
    public void delete(int id) {
        userDao.deleteById(id);
    }


}

