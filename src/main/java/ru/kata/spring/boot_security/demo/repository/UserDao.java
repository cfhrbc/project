package ru.kata.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select u from User u left join fetch u.roles where u.name=:username")
    Optional<User> getUserByName(@Param("username") String username);


}
