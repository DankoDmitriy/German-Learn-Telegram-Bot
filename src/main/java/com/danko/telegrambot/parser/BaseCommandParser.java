package com.danko.telegrambot.parser;

import com.danko.telegrambot.entity.Command;
import com.danko.telegrambot.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BaseCommandParser implements CommandParser {
    private static final String PREFIX_FOR_COMMAND = "/";

    @Override
    public Optional<Command> parseCommand(String message) {
        if (StringUtil.isBlank(message)) {
            return Optional.empty();
        }
        String trimText = StringUtil.trim(message);
        if (isCommand(trimText)) {
            String commandName = getDelimitedCommandFromText(trimText).substring(1);
            return Command.parseCommand(commandName);
        }
        return Optional.empty();
    }

    private String getDelimitedCommandFromText(String trimText) {
        String commandText;
        if (trimText.contains(" ")) {
            int indexOfSpace = trimText.indexOf(" ");
            commandText = trimText.substring(0, indexOfSpace);
        } else commandText = trimText;
        return commandText;
    }

    private boolean isCommand(String text) {
        return text.startsWith(PREFIX_FOR_COMMAND);
    }
}
