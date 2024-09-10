package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;

import ru.kata.spring.boot_security.demo.repository.RoleDao;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {


    private final RoleDao roleDao;


    public RoleService(RoleDao roleDao) {

        this.roleDao = roleDao;

    }

    public Role roleName(String name) {
        return roleDao.findByName(name);
    }

    public Role findRoleById(Integer id) {
        return roleDao.findRoleById(id);
    }


    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }


}

