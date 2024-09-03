package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Метод для отображения профиля пользователя
    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        // Получаем информацию о текущем авторизованном пользователе
        String username = principal.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "user-profile"; // Страница профиля пользователя
    }
}
/*
@Controller
@RequestMapping("/user")
public class UserController {
    UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }


    @GetMapping()
    public String dashboardPageList(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userDao.getUserByName(currentUser.getUsername()).orElseThrow();
        model.addAttribute("currentUser", user);

        return "infoUserOneNewStyle";
    }

}

 */
