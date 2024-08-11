package ru.t1academy.java.hw1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
@Slf4j
public class ExceptionAspect {

    @Pointcut("within(ru.t1academy.java.hw1.service..*)")
    public void exceptionPointCut() {
    }

    @AfterThrowing(value = "exceptionPointCut()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();

        log.error("Method {} throws exception: {}", methodName, ex.getMessage());
    }
}
