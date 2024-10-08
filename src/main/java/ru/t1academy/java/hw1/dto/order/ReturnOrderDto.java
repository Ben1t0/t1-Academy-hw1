package ru.t1academy.java.hw1.dto.order;

import lombok.Builder;

@Builder
public record ReturnOrderDto(
        long id,
        String description,
        String status,
        long customerId
) {
}