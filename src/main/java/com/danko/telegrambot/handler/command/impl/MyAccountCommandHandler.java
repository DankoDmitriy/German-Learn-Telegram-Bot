package com.danko.telegrambot.handler.command.impl;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.handler.command.CommandHandler;
import com.danko.telegrambot.storage.LocalStorageTG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class MyAccountCommandHandler implements CommandHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    private LocalStorageTG localStorageTG;

    @Override
    public void handleCommand(Message message) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(message.getChatId())
                        .parseMode("html")
                        .text(profileInformation(message.getChatId()))
                        .build());
    }

    private String profileInformation(Long tgId) {
        Optional<User> optionalUser = localStorageTG.getUser(tgId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return "Ваш профиль:\n\n"
                    + "Уровень языка: " +
                    (user.getLanguageLevel() != null ? user.getLanguageLevel().getName() : "Не выбран.") +
                    "\n" +
                    "Текущая тема: " +
                    (user.getTopic() != null ? user.getTopic().getName() : "Не выбран.");
        }
        return "Профиль временно не доступен либо создание профиля запрещено.";
    }

    @Override
    public Command getCommand() {
        return Command.MY_ACCOUNT;
    }
}
