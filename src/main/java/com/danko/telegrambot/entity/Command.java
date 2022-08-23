package com.danko.telegrambot.entity;

import com.danko.telegrambot.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Command {
    ABOUT("about", "About bot"),
    MY_ACCOUNT("my_account", "Information about you"),
    LEVEL_LIST("level_list", "Level list"),
    TOPIC_LIST("topic_list", "Topic list");

    private final String name;
    private final String desc;

    public static Optional<Command> parseCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return Optional.empty();
        }
        String formatName = StringUtil.trim(command).toLowerCase();
        return Stream.of(values()).filter(c -> c.name.equalsIgnoreCase(formatName)).findFirst();
    }

}
