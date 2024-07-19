package com.company.employee.dto.response;

import java.time.LocalDate;

public record ResponseEmployee(
        String name,
        String email,
        String tell,
        LocalDate joined) {
}
