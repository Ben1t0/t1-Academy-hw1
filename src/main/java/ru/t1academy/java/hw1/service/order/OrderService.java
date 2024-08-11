package ru.t1academy.java.hw1.service.order;

import ru.t1academy.java.hw1.dto.order.OrderDto;
import ru.t1academy.java.hw1.dto.order.ReturnOrderDto;

import java.util.List;

public interface OrderService {
    ReturnOrderDto getById(long orderId);

    List<ReturnOrderDto> getAll();

    ReturnOrderDto addOrder(OrderDto orderDto);

    ReturnOrderDto updateOrder(long orderId, OrderDto orderDto);

    void deleteOrder(long orderId);
}
