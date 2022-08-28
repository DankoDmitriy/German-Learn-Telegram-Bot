package com.danko.telegrambot.service;

import com.danko.telegrambot.entity.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    List<Topic> getAllTopicByLanguageIdAndStatus(Long languageId, Boolean status);

    Boolean checkTopic(String name, Boolean status);

    Optional<Topic> getTopicByNameAndStatus(String name, Boolean status);
}
