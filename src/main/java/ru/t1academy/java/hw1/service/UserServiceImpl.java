package ru.t1academy.java.hw1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1academy.java.hw1.annotation.LoggingAnnotation;
import ru.t1academy.java.hw1.dto.user.CreateUserDto;
import ru.t1academy.java.hw1.dto.user.ReturnUserDto;
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

    @LoggingAnnotation
    @Override
    @Transactional(readOnly = true)
    public ReturnUserDto getById(long userId) {
        return userMapper.toDto(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id %d not found".formatted(userId))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnUserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ReturnUserDto addUser(CreateUserDto createUserDto) {
        User user = userMapper.fromDto(createUserDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
