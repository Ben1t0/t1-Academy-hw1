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

    @Pointcut("within(ru.t1academy.java.hw1..*) && @target(ru.t1academy.java.hw1.annotation.Logging)")
    public void loggingAnnotationClassMethodsPointCut() {
    }

    @Pointcut("within(ru.t1academy.java.hw1..*) && @annotation(ru.t1academy.java.hw1.annotation.Logging)")
    public void loggingAnnotationMethodPointCut() {
    }

    @Around("loggingAnnotationClassMethodsPointCut() || loggingAnnotationMethodPointCut()")
    public Object loggingAroundAspect(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object args = pjp.getArgs();

        log.info("Called method {}, with args {}", methodName, args);
        var result = pjp.proceed();

        log.info("Method {} execution result {}", methodName, result);
        return result;
    }
}
