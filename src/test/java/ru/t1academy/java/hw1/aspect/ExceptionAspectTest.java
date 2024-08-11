package ru.t1academy.java.hw1.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.aspectj.lang.JoinPoint;
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
import ru.t1academy.java.hw1.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionAspectTest {
    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    private final Logger logger = (Logger) LogManager.getLogger(ExceptionAspect.class);

    @InjectMocks
    private ExceptionAspect aspect;

    @Mock
    private Appender appender;
    @Captor
    private ArgumentCaptor<LogEvent> logCaptor;

    @BeforeEach
    public void setUp() {
        when(appender.getName()).thenReturn("Mock appender");
        when(appender.isStarted()).thenReturn(true);
        logger.addAppender(appender);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void successfullyLogging() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        aspect.afterThrowingAdvice(joinPoint, new NotFoundException("Item not found"));
        verifyLogMessages("Method methodName throws exception: Item not found");
    }

    private void verifyLogMessages(String... messages) {
        verify(appender, times(messages.length)).append(logCaptor.capture());

        int i = 0;
        for (LogEvent loggingEvent : logCaptor.getAllValues()) {
            assertEquals(messages[i++], loggingEvent.getMessage().getFormattedMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        logger.removeAppender(appender);
    }
}