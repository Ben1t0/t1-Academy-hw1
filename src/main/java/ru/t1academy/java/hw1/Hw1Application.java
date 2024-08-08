package ru.t1academy.java.hw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Hw1Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw1Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ApplicationReady() {
        System.out.println("ready");
    }

}
