package com.danko.telegrambot.service.impl;

import com.danko.telegrambot.entity.Topic;
import com.danko.telegrambot.persistence.TopicRepository;
import com.danko.telegrambot.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopicByLanguageIdAndStatus(Long languageId, Boolean status) {
        return topicRepository.findAllByStatus_AndLevelId(status, languageId);
    }

    @Override
    public Boolean checkTopic(String name, Boolean status) {
        return topicRepository.findByNameAndStatus(name, status).isPresent();
    }

    @Override
    public Optional<Topic> getTopicByNameAndStatus(String name, Boolean status) {
        return topicRepository.findByNameAndStatus(name, status);
    }
}
