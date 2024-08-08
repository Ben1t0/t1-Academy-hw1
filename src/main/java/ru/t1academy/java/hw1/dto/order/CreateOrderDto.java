package ru.t1academy.java.hw1.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDto(
        @NotBlank
        String description,
        @NotNull
        Long customerId) {
}
