package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    private final RoleDao roleDao;

    public UserService(@Lazy PasswordEncoder passwordEncoder, UserDao userDao, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }


    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var user = userDao.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    public List<User> showAllUsers() {
        return userDao.findAllUsers();
    }


    public void delete(int id) {
        userDao.deleteById(id);
    }


    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        var existingRoles = new HashSet<Role>();
        for (var role : user.getRoles()) {
            var existingRole = roleDao.findByName(role.getName());
            if (existingRole != null) {
                existingRoles.add(existingRole);
            } else {
                existingRoles.add(role);
            }
        }
        user.setRoles(existingRoles);


        userDao.save(user);
    }

    public User findUserById(Integer id) {
        return userDao.findById(id);
    }


}

