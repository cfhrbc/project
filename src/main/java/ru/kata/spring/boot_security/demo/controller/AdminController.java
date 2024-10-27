package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping
    public String adminHome() {
        return "redirect:/admin/users"; // Перенаправление на список пользователей
    }


    // Метод для отображения списка всех пользователей (READ)
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user-list"; // Страница с отображением всех пользователей
    }

    // Метод для отображения формы добавления нового пользователя (CREATE)
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User()); // Новый объект User для формы
        model.addAttribute("roles", roleService.findAllRoles()); // Список всех ролей
        return "add-user"; // Страница с формой добавления пользователя
    }

    // Метод для обработки добавления нового пользователя (CREATE)
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user); // Сохранение нового пользователя
        return "redirect:/admin/users"; // Перенаправление на список пользователей
    }

    // Метод для отображения формы редактирования пользователя (READ + UPDATE)
    @GetMapping("/users/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        return "edit-user"; // Страница для редактирования данных пользователя
    }

    // Метод для обработки обновления данных пользователя (UPDATE)
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        userService.saveUser(user); // Сохранение изменений пользователя
        return "redirect:/admin/users"; // Перенаправление на список пользователей
    }

    // Метод для удаления пользователя (DELETE)
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id); // Удаление пользователя
        return "redirect:/admin/users"; // Перенаправление на список пользователей
    }
}
/*
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.showAllUser());
        model.addAttribute("roles", roleService.findAll());
        return "mainPageNewStyle";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "showId";
    }

    @GetMapping ("/create")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.save(user);
        return "redirect:/admin";
    }



    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return  "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}

 */
