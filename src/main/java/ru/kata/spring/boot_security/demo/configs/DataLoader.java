package ru.kata.spring.boot_security.demo.configs;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import javax.transaction.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleDao roleDao;

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role adminRole = roleDao.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleDao.save(adminRole);
        }

        // Проверка и создание роли 'ROLE_USER'
        Role userRole = roleDao.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleDao.save(userRole);
        }

        // Создаем пользователя с ролью админ
        if (userDao.findByName("admin") == null) {
            User admin = new User();
            admin.setName("admin");
            admin.setSurname("Adminov");
            admin.setAge(44);
            admin.setEmail("sdcsrcscs@bk.ru");
            admin.setPassword(passwordEncoder.encode("admin"));


            admin.addRole(adminRole); // Присваиваем роль пользователю

            userDao.save(admin);


        }

    }
}




