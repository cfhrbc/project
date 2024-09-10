package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    // Метод для получения списка пользователей в формате JSON
    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsersJson() {
        return userService.showAllUser();  // Возвращаем список пользователей в формате JSON
    }

    @GetMapping()
    public String getUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.showAllUser());
        model.addAttribute("roles", roleService.findAllRoles());
        return "adminHome";
    }


    @GetMapping("/adminHome")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "/adminHome";
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Возвращаем ошибку валидации в формате JSON с кодом 400
            return ResponseEntity.badRequest().body("Ошибка валидации данных пользователя");
        }

        Set<Integer> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());

        // Преобразуем идентификаторы в объекты Role
        Set<Role> roles = roleIds.stream()
                .map(roleService::findRoleById)
                .collect(Collectors.toSet());
        user.setRoles(roles);

        // Сохраняем пользователя
        userService.saveUser(user);

        // Возвращаем JSON-ответ с сообщением об успехе и кодом 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан");
    }



    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") List<String> roleIds,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        List<Integer> roleIdsAsIntegers = roleIds.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        userService.update(user, roleIdsAsIntegers);

        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}