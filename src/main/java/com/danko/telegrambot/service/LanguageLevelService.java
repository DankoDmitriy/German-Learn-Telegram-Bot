package com.danko.telegrambot.service;

import com.danko.telegrambot.entity.LanguageLevel;

import java.util.List;
import java.util.Optional;

public interface LanguageLevelService {

    List<LanguageLevel> getActive();

    Boolean checkLanguageLevel(String name, Boolean status);

    Optional<LanguageLevel> getLanguageByNameAndStatus(String name, Boolean status);
}
