package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final EntityManager entityManager;

    public RoleServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> findAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role findRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        Role existingRole = entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", role.getName())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existingRole == null) {
            entityManager.persist(role);  // Сохраняем новую роль, если она не найдена
        } else {
            // Ничего не делаем, если роль уже существует, или можно обновить другие поля, если необходимо
            entityManager.merge(existingRole);  // Обновляем роль, если нужны другие изменения
        }
    }


    @Override
    public Role findByName(String roleName) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :roleName", Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}




/*
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleDao;


    public RoleServiceImpl(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}

 */

