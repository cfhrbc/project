package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.SocialMedia;

import java.util.List;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {

    List<SocialMedia> findAllByUserId(Long userId);
}
