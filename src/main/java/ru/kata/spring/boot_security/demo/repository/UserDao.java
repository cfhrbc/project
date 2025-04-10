package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public User findByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    public List<User> findAllWithFilters(Map<String, String> filters, String sortBy, String sortOrder) {
        var cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Join<Object, Object> rolesJoin = null;
        if (filters.containsKey("role")) {
            rolesJoin = root.join("roles");
        }
        var metamodel = entityManager.getMetamodel();
        EntityType<User> entityType = metamodel.entity(User.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            if ("role".equals(field)) {
                predicates.add(cb.equal(rolesJoin.get("name"), value));
            } else if (entityType.getDeclaredSingularAttributes().stream().anyMatch(attr -> attr.getName().equals(field))) {
                predicates.add(cb.equal(root.get(field), value));
            } else {
                System.out.println("Внимание: поле '" + field + "' отсутствует в сущности User, пропускаем.");
            }
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            if (entityType.getDeclaredSingularAttributes().stream().anyMatch(attr -> attr.getName().equals(sortBy))) {
                Path<Object> sortPath = root.get(sortBy);
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    query.orderBy(cb.desc(sortPath));
                } else {
                    query.orderBy(cb.asc(sortPath));
                }
            } else {
                throw new IllegalArgumentException("Неверный параметр sortBy: " + sortBy);
            }
        }

        return entityManager.createQuery(query).getResultList();
    }

    public void deleteById(Integer id) {
        var user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }
}