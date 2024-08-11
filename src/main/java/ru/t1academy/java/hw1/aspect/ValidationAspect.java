package ru.t1academy.java.hw1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1academy.java.hw1.annotation.Exception;
import ru.t1academy.java.hw1.dto.order.OrderDto;
import ru.t1academy.java.hw1.exception.ValidationException;

@Component
@Aspect
@Order(2)
@Slf4j
public class ValidationAspect {

    @Exception
    @Before("execution(* create*(.., @ru.t1academy.java.hw1.annotation.Valid " +
            "(ru.t1academy.java.hw1.dto.order.OrderDto ), ..)) && args(orderDto)")
    public void advice(OrderDto orderDto) {
        StringBuilder sb = new StringBuilder();

        if (orderDto.description() == null) {
            sb.append("Err: Description field must not be null  ");

        } else if (orderDto.description().isBlank()) {
            sb.append("Err: Description field must not be blank  ");
        }
        if (orderDto.customerId() == null) {
            sb.append("Err: customerId field must not be null  ");
        }
        if (!sb.isEmpty()) {
            sb.insert(0, "Validation Error: ");
            log.error(sb.toString());
            throw new ValidationException(sb.toString());
        }
    }
}
