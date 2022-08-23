package com.danko.telegrambot.persistence;

import com.danko.telegrambot.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByStatus(Boolean status);
}
