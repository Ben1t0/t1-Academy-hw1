package ru.t1academy.java.hw1.aspect;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private final Logger logger = (Logger) LogManager.getLogger(LoggingAspect.class);

    @InjectMocks
    private LoggingAspect aspect;

    @Mock
    private Appender appender;
    @Captor
    private ArgumentCaptor<LogEvent> logCaptor;

    @BeforeEach()
    public void setUp(){
        when(appender.getName()).thenReturn("Mock appender");
        when(appender.isStarted()).thenReturn(true);
        logger.addAppender(appender);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void successfullyLogging() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.proceed()).thenReturn(null);
        aspect.loggingAroundAspect(joinPoint);
        verify(appender, times(2)).append(logCaptor.capture());
        assertThat(logCaptor.getValue().getMessage().getFormattedMessage(),
                equalTo("Method methodName execution result null"));

        verify(joinPoint, times(1)).proceed();
    }

    @AfterEach
    public void tearDown(){
        logger.removeAppender(appender);
    }
}