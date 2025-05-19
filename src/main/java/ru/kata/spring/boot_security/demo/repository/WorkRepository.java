package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Work;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Optional<Work> findWorkByUsersId(Long userId);
}
