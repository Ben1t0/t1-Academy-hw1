package ru.t1academy.java.hw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1academy.java.hw1.dto.user.ReturnUserDto;
import ru.t1academy.java.hw1.dto.user.UserDto;
import ru.t1academy.java.hw1.service.user.UserService;
import ru.t1academy.java.hw2.filter.RequestAndResponseFilter;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(ru.t1academy.java.hw2.configuration.WebLoggingAutoConfiguration.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private final Logger logger = (Logger) LogManager.getLogger(RequestAndResponseFilter.class);

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
    public void testWebLoggingStarterTest() throws Exception {
        ReturnUserDto returnUserDto = ReturnUserDto.builder()
                .id(1)
                .name("name")
                .email("name@name")
                .build();

        when(userService.addUser(any()))
                .thenReturn(returnUserDto);

        String targetMessage = """

                -----------------------------
                >>>>>>>>>>REQUEST>>>>>>>>>>>
                METHOD: POST
                URI: /api/v1/users
                ----------HEADERS----------
                HEADER Content-Type : application/json;charset=UTF-8
                HEADER Accept : application/json
                HEADER Content-Length : 35
                BODY:  {"name":"name","email":"name@name"}
                -----------------------------
                <<<<<<<<<<RESPONSE<<<<<<<<<<
                STATUS: 201
                ----------HEADERS----------
                HEADER Content-Type : application/json
                HEADER Content-Length : 42
                BODY:  {"id":1,"name":"name","email":"name@name"}
                -----------------------------
                Execution time:""";

        mvc.perform(post("/api/v1/users")
                        .content(mapper.writeValueAsString(new UserDto("name", "name@name")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(returnUserDto)));


        verify(appender, times(1)).append(logCaptor.capture());
        String returnedString = logCaptor.getAllValues().get(0).getMessage().getFormattedMessage();

        Pattern p = Pattern.compile("Execution time: [0-9]+");
        Matcher m = p.matcher(returnedString);
        returnedString = m.replaceFirst("Execution time:");
        assertEquals(targetMessage, returnedString);
    }

    @AfterEach
    public void tearDown() {
        logger.removeAppender(appender);
    }
}