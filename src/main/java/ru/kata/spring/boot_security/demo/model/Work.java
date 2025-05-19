package ru.kata.spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work")
@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();;
}
