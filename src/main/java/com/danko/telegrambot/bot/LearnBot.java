package com.danko.telegrambot.bot;

import com.danko.telegrambot.entity.Command;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LearnBot extends TelegramLongPollingBot {
    private static final String LOG_MESSAGE_ERROR_REGISTRATION_BOT = "Bot has been not registration: ";
    private static final String LOG_MESSAGE_ERROR_BOT_MENU_INIT = "Bot menu did not init : ";
    private static final String LOG_MESSAGE_INFO_REGISTERED_BOT = "Bot registered.";
    private static final Logger logger = LogManager.getLogger();

    @Value("${telegram.bot.username}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String token;

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            logger.log(Level.INFO, LOG_MESSAGE_INFO_REGISTERED_BOT);
        } catch (TelegramApiException e) {
            logger.log(Level.ERROR, LOG_MESSAGE_ERROR_REGISTRATION_BOT + e.getMessage());
        }
        setupCommandsInMenu();
    }

    private void setupCommandsInMenu() {
        try {
            List<BotCommand> commands =
                    Arrays.stream(Command.values())
                            .map(c -> BotCommand.builder().command(c.getName()).description(c.getDesc()).build())
                            .collect(Collectors.toList());
            execute(SetMyCommands.builder().commands(commands).build());
        } catch (Exception e) {
            logger.log(Level.ERROR, LOG_MESSAGE_ERROR_BOT_MENU_INIT + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        logger.log(Level.INFO, "I had had a message");
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
