package com.danko.telegrambot.persistence;

import com.danko.telegrambot.entity.LanguageLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageLevelRepository extends JpaRepository<LanguageLevel, Long> {

    List<LanguageLevel> findAllByStatus(Boolean status);

    Optional<LanguageLevel> findByNameAndStatus(String name, Boolean status);
}
