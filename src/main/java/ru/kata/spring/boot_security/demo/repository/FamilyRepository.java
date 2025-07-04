package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Family;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    List<Family> findAllByUserId(Long userId);
}
