package ru.t1academy.java.hw1.service;

import ru.t1academy.java.hw1.dto.order.CreateOrderDto;
import ru.t1academy.java.hw1.dto.order.ReturnOrderDto;

import java.util.List;

public interface OrderService {
    ReturnOrderDto getById(long orderId);

    List<ReturnOrderDto> getAll();

    ReturnOrderDto addOrder(CreateOrderDto createOrderDto);
}
