package com.danko.telegrambot.persistence;

import com.danko.telegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTgId(Long tgId);
}
