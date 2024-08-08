package ru.t1academy.java.hw1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1academy.java.hw1.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
