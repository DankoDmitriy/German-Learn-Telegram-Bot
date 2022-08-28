package com.danko.telegrambot.service;

import com.danko.telegrambot.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByTgId(Long tgId);

    User save(User user);

    User update(User user);
}
