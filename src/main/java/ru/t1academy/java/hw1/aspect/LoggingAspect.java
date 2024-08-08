package ru.t1academy.java.hw1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
@Slf4j
public class LoggingAspect {

    @Pointcut("within(ru.t1academy.java.hw1..*) && @target(ru.t1academy.java.hw1.annotation.LoggingAnnotation)")
    public void loggingAnnotationClassMethodsPointCut() {
    }

    @Pointcut("within(ru.t1academy.java.hw1..*) && @annotation(ru.t1academy.java.hw1.annotation.LoggingAnnotation)")
    public void loggingAnnotationMethodPointCut() {
    }

    @Around("loggingAnnotationClassMethodsPointCut() || loggingAnnotationMethodPointCut()")
    public Object loggingAroundAspect(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Прокси : " + pjp.getThis().getClass().getName());
        System.out.println("Класс : " + pjp.getTarget().getClass().getName());

        System.out.println("Вызов метода : " + pjp.getSignature().getName());
        var result = pjp.proceed();

        return result;
    }
/*
    @Before("loggingAnnotationClassMethodsPointCut() || loggingAnnotationMethodPointCut()")
    public void loggingBeforeAspect(JoinPoint joinPoint) {
        System.out.println("Прокси : " + joinPoint.getThis().getClass().getName());
        System.out.println("Класс : " + joinPoint.getTarget().getClass().getName());

        System.out.println("Вызов метода : " + joinPoint.getSignature().getName());
    }*/
}
