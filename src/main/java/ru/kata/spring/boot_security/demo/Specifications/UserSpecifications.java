package ru.kata.spring.boot_security.demo.Specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserSpecifications {

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
