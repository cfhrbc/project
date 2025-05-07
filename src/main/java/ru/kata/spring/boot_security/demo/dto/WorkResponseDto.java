package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkResponseDto {

    private Long id;
    private String company;
    private String position;
    private String startDate;
    private String endDate;
    private Long userId;
}
