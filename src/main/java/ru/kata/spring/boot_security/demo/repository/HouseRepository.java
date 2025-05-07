package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.House;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findAllByUserId(Long userId);
}
