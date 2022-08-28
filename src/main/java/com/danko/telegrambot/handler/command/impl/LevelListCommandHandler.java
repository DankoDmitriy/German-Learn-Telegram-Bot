package com.danko.telegrambot.handler.command.impl;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.entity.LanguageLevel;
import com.danko.telegrambot.handler.command.CommandHandler;
import com.danko.telegrambot.service.LanguageLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class LevelListCommandHandler implements CommandHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    LanguageLevelService languageLevelService;

    @Override
    public void handleCommand(Message message) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(message.getChatId())
                        .parseMode("html")
                        .text(getTextOfLanguageList())
                        .build()
        );
    }

    private String getTextOfLanguageList() {
        List<LanguageLevel> languageLevels = languageLevelService.getActive();
        StringBuilder stringBuilder = new StringBuilder().append("Доступные уровни языка:\n\n");

        languageLevels.forEach(languageLevel -> stringBuilder.append(languageLevel.getName()).append(" \n"));

        stringBuilder.append("\nДля выбора уровня нажмите: /set_language_level");

        return stringBuilder.toString();
    }

    @Override
    public Command getCommand() {
        return Command.LEVEL_LIST;
    }
}
