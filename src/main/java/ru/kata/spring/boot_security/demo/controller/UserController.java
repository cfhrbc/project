package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

@Controller
@RequestMapping("/user")
public class UserController {
    UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }


    @GetMapping()
    public String dashboardPageList(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userDao.getUserByName(currentUser.getUsername()).orElseThrow();
        model.addAttribute("currentUser", user);

        return "infoUserOneNewStyle";
    }

}
