package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class RoleRepository {
    private final EntityManager entityManager;


    public RoleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Role findByName(String name) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Role role) {
        if (role.getId() == null) {
            // Если у роли нет ID, то это новая роль, и мы её сохраняем
            entityManager.persist(role);
        } else {
            // Если у роли уже есть ID, то обновляем её данные
            entityManager.merge(role);
        }
    }
}





/*
Optional<Role> findByName(String name);
*/