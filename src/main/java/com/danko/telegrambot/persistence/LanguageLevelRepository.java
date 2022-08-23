package com.danko.telegrambot.persistence;

import com.danko.telegrambot.entity.LanguageLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageLevelRepository extends JpaRepository<LanguageLevel, Long> {
    List<LanguageLevel> findAllByStatus(Boolean status);
}
