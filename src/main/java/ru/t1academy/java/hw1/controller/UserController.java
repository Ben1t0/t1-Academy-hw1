package ru.t1academy.java.hw1.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.t1academy.java.hw1.dto.user.CreateUserDto;
import ru.t1academy.java.hw1.dto.user.ReturnUserDto;
import ru.t1academy.java.hw1.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/v1/users/{userId}")
    public ReturnUserDto getUserById(@PathVariable long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/api/v1/users")
    public List<ReturnUserDto> getAllOrders() {
        return userService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/users")
    public ReturnUserDto createOrder(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.addUser(createUserDto);
    }
}
