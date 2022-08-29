package com.danko.telegrambot.handler.command.impl;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.handler.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AboutCommandHandler implements CommandHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Override
    public void handleCommand(Message message) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(message.getChatId())
                        .parseMode("html")
                        .text(
                                "Этот бот обучает немецкому языку опираясь на учебник: Menschen\n\n"
                                        + "Автор бота, на момент его написания не имеет значительных"
                                        + " знаний в немецком языке.\n\n"
                                        + "Этот бот был написан в целях изучения языка программирования Java.\n\n"
                                        + "Бот написан по уроку от MJC School.\n\n"
                        )
                        .build());
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT;
    }
}
