package ru.t1academy.java.hw1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1academy.java.hw1.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
