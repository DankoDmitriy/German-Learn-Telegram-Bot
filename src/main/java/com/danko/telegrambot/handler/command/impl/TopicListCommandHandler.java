package com.danko.telegrambot.handler.command.impl;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.entity.Topic;
import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.handler.command.CommandHandler;
import com.danko.telegrambot.service.LocalStorageTGService;
import com.danko.telegrambot.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Component
public class TopicListCommandHandler implements CommandHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    private TopicService topicService;

    @Autowired
    private LocalStorageTGService localStorageTGService;

    @Override
    public void handleCommand(Message message) throws TelegramApiException {
        Long tgId = message.getChatId();
        Optional<User> optionalUser = localStorageTGService.getUser(tgId);

        if (!optionalUser.isPresent() || optionalUser.get().getLanguageLevel() == null) {
            sendMessage(tgId, "Для просмотра тем, необходимо выбрать уровень языка. /level_list");
        }
        sendMessage(tgId, getTextOfTopicList(optionalUser.get()));
    }

    private void sendMessage(Long chatId, String text) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("html")
                        .text(text)
                        .build()
        );
    }

    private String getTextOfTopicList(User user) {
        List<Topic> topicList = topicService.getAllTopicByLanguageIdAndStatus(user.getLanguageLevel().getId(), true);

        StringBuilder stringBuilder = new StringBuilder().append("Доступные темы:\n\n");

        topicList.forEach(topic -> stringBuilder.append(topic.getName()).append(" \n"));

        stringBuilder.append("\nДля выбора темы: /set_topic");

        return stringBuilder.toString();
    }

    @Override
    public Command getCommand() {
        return Command.TOPIC_LIST;
    }
}
