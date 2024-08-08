package ru.t1academy.java.hw1.service;

import ru.t1academy.java.hw1.dto.user.CreateUserDto;
import ru.t1academy.java.hw1.dto.user.ReturnUserDto;

import java.util.List;

public interface UserService {
    ReturnUserDto getById(long orderId);

    List<ReturnUserDto> getAll();

    ReturnUserDto addUser(CreateUserDto createUserDto);
}
