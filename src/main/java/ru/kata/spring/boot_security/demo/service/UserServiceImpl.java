package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service

public class UserServiceImpl implements UserService{
    private final EntityManager entityManager;

    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Set<Role> managedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role managedRole = entityManager.merge(role);  // вместо persist используем merge
            managedRoles.add(managedRole);
        }
        user.setRoles(managedRoles);

        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.name = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}







/*
@Service
public class UserServiceImpl implements UserService {


    final UserRepository userDao;
    final RoleRepository roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
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

 */

