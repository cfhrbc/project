package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;


public interface RoleService {
    List<Role> findAllRoles();
    Role findRoleById(int id);
    void saveRole(Role role);
    Role findByName(String roleName);
}





/*
public interface RoleService {
    List<Role> findAll();
}


 */
