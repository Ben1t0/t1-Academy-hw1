package ru.t1academy.java.hw1.service.user;

import ru.t1academy.java.hw1.dto.user.ReturnUserDto;
import ru.t1academy.java.hw1.dto.user.UserDto;

import java.util.List;

public interface UserService {
    ReturnUserDto getById(long orderId);

    List<ReturnUserDto> getAll();

    ReturnUserDto addUser(UserDto userDto);

    ReturnUserDto updateUser(long userId, UserDto userDto);

    void deleteUser(long userId);
}
