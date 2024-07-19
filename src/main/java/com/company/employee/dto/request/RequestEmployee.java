package com.company.employee.dto.request;

import jakarta.validation.constraints.NotNull;

public record RequestEmployee(
        @NotNull String name,
        @NotNull String email,
        @NotNull String tell,
        @NotNull String joined) {
}
