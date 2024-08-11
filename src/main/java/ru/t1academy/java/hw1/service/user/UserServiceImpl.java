package ru.t1academy.java.hw1.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1academy.java.hw1.annotation.Logging;
import ru.t1academy.java.hw1.dto.user.ReturnUserDto;
import ru.t1academy.java.hw1.dto.user.UserDto;
import ru.t1academy.java.hw1.dto.user.UserMapper;
import ru.t1academy.java.hw1.exception.NotFoundException;
import ru.t1academy.java.hw1.model.User;
import ru.t1academy.java.hw1.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Logging
    @Override
    @Transactional(readOnly = true)
    public ReturnUserDto getById(long userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id %d not found".formatted(userId))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnUserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    @Logging
    public ReturnUserDto addUser(UserDto userDto) {
        User user = userMapper.fromDto(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    @Logging
    public ReturnUserDto updateUser(long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id %d not found".formatted(userId)));

        if (userDto.email() != null && !userDto.email().isBlank()) {
            user.setEmail(userDto.email());
        }
        if (userDto.name() != null && !userDto.name().isBlank()) {
            user.setName(user.getName());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.findById(userId);
    }
}
