package com.danko.telegrambot.handler.update;

import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.handler.command.CommandHandler;
import com.danko.telegrambot.handler.command.CommandHandlerFactory;
import com.danko.telegrambot.parser.CommandParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class CommandUpdateHandler implements UpdateHandler {
    @Autowired
    private CommandParser commandParser;
    @Autowired
    private CommandHandlerFactory commandHandlerFactory;

    @Override
    public boolean handleUpdate(Update update) throws TelegramApiException {
        if (!update.hasMessage()) {
            return false;
        }
        Message message = update.getMessage();
        if (!message.hasText()) {
            return false;
        }
        String text = message.getText();
        Optional<Command> command = commandParser.parseCommand(text);
        if (!command.isPresent()) {
            return false;
        }
        handleCommand(update, command.get());

        return true;
    }

    private void handleCommand(Update update, Command command)
            throws TelegramApiException {
        CommandHandler commandHandler = commandHandlerFactory.getHandler(command);
        commandHandler.handleCommand(update.getMessage());
    }

    @Override
    public UpdateHandlerStage getStage() {
        return UpdateHandlerStage.COMMAND;
    }
}
