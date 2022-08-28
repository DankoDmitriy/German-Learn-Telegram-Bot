package com.danko.telegrambot.service.impl;

import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.persistence.UserRepository;
import com.danko.telegrambot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public Optional<User> findUserByTgId(Long tgId) {
        return userRepository.findByTgId(tgId);
    }

    @Override
    public User save(User user) {
        user.setUpdate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
