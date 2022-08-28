package com.danko.telegrambot.handler.command;

import com.danko.telegrambot.entity.Command;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface CommandHandler {
    void handleCommand(Message message) throws TelegramApiException;

    Command getCommand();
}
