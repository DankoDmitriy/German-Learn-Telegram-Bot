package com.danko.telegrambot.persistence;

import com.danko.telegrambot.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByStatus(Boolean status);

    List<Topic> findAllByStatus_AndLevelId(Boolean status, Long levelId);

    Optional<Topic> findByNameAndStatus(String name, Boolean status);
}
