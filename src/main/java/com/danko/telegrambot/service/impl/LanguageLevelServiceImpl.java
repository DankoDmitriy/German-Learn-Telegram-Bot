package com.danko.telegrambot.service.impl;

import com.danko.telegrambot.entity.LanguageLevel;
import com.danko.telegrambot.persistence.LanguageLevelRepository;
import com.danko.telegrambot.service.LanguageLevelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LanguageLevelServiceImpl implements LanguageLevelService {
    private LanguageLevelRepository languageLevelRepository;

    @Override
    public List<LanguageLevel> getActive() {
        return languageLevelRepository.findAllByStatus(true);
    }

    @Override
    public Boolean checkLanguageLevel(String name, Boolean status) {
        return languageLevelRepository.findByNameAndStatus(name, status).isPresent();
    }

    @Override
    public Optional<LanguageLevel> getLanguageByNameAndStatus(String name, Boolean status) {
        return languageLevelRepository.findByNameAndStatus(name, status);
    }
}
