package ru.t1academy.java.hw1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.t1academy.java.hw1.annotation.Valid;
import ru.t1academy.java.hw1.dto.order.OrderDto;
import ru.t1academy.java.hw1.dto.order.ReturnOrderDto;
import ru.t1academy.java.hw1.service.order.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/api/v1/orders/{orderId}")
    public ReturnOrderDto getOrderById(@PathVariable long orderId) {
        return orderService.getById(orderId);
    }

    @GetMapping("/api/v1/orders")
    public List<ReturnOrderDto> getAllOrders() {
        return orderService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/orders")
    public ReturnOrderDto createOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.addOrder(orderDto);
    }

    @PutMapping("/api/v1/orders/{orderId}")
    public ReturnOrderDto updateOrder(@PathVariable long orderId, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(orderId, orderDto);
    }

    @DeleteMapping("/api/v1/orders/{orderId}")
    public void deleteOrder(@PathVariable long orderId) {
        orderService.deleteOrder(orderId);
    }
}
