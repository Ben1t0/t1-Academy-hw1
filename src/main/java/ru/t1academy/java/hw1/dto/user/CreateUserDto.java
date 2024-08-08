package ru.t1academy.java.hw1.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String name, @Email String email) {
}
