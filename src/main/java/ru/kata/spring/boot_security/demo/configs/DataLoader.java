package ru.kata.spring.boot_security.demo.configs;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Проверяем, существует ли роль "ROLE_ADMIN"
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            // Если роли нет, создаем и сохраняем ее
            adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Проверяем, существует ли роль "ROLE_USER"
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            // Если роли нет, создаем и сохраняем ее
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Проверяем, существует ли пользователь "admin"
        User adminUser = userRepository.findByUsername("admin");
        if (adminUser == null) {
            // Если пользователя нет, создаем и сохраняем его
            adminUser = new User();
            adminUser.setName("admin");
            adminUser.setSurname("admin");
            adminUser.setEmail("admin@mail.com");
            adminUser.setAge(39);
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.getRoles().add(adminRole);
            userRepository.save(adminUser);
        }
    }
}


    /*


    final RoleDaoImpl roleDao;
    final PasswordEncoder passwordEncoder;
    final UserDao userDao;

    public DataLoader(RoleDaoImpl roleDao, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = roleDao.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role("ROLE_ADMIN");
                    return roleDao.save(role);

                });

        Role userRole = roleDao.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role("ROLE_USER");
                    return roleDao.save(role);
                });

        User adminUser = userDao.getUserByName("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setName("admin");
                    user.setSurname("admin");
                    user.setEmail("admin@mail.com");
                    user.setAge(39);
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.getRoles().add(adminRole);
                    return userDao.save(user);

                });

    }
}
*/




