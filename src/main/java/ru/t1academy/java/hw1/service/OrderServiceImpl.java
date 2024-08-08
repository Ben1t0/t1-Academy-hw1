package ru.t1academy.java.hw1.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1academy.java.hw1.annotation.LoggingAnnotation;
import ru.t1academy.java.hw1.dto.order.CreateOrderDto;
import ru.t1academy.java.hw1.dto.order.OrderMapper;
import ru.t1academy.java.hw1.dto.order.ReturnOrderDto;
import ru.t1academy.java.hw1.exception.NotFoundException;
import ru.t1academy.java.hw1.model.Order;
import ru.t1academy.java.hw1.model.OrderStatus;
import ru.t1academy.java.hw1.model.User;
import ru.t1academy.java.hw1.repository.OrderRepository;
import ru.t1academy.java.hw1.repository.UserRepository;

import java.util.List;

@Service
@LoggingAnnotation
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ReturnOrderDto getById(long orderId) {
        return orderMapper.toDto(orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnOrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ReturnOrderDto addOrder(CreateOrderDto createOrderDto) {
        User customer = userRepository.findById(createOrderDto.customerId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Order order = Order.builder()
                .description(createOrderDto.description())
                .status(OrderStatus.NEW)
                .customer(customer)
                .build();

        return orderMapper.toDto(orderRepository.save(order));
    }
}
