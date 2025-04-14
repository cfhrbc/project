package ru.kata.spring.boot_security.demo.Specifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserSpecifications {

    private static final Logger log = LoggerFactory.getLogger(UserSpecifications.class);

    public static Specification<User> withFilters(Map<String, String> filters) {
        return (root, query, CriteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.containsKey("role")) {
                Join<Object, Object> rolesJoin = root.join("roles", JoinType.LEFT);
                predicates.add(CriteriaBuilder.equal(rolesJoin.get("name"), filters.get("role")));
            }

            filters.forEach((field, value) -> {
                if (!"role".equals(field)) {
                    try {
                        root.get(field);
                        predicates.add(CriteriaBuilder.equal(root.get(field), value));
                    } catch (IllegalArgumentException e) {
                        log.warn("Внимание: поле '{}' отсутствует. Пропускаем.", field);
                    }
                }
            });

            return CriteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
