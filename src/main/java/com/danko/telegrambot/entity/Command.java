package com.danko.telegrambot.entity;

import com.danko.telegrambot.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Command {
    ABOUT("about", "О боте"),
    MY_ACCOUNT("my_account", "Информация о Вашей записи"),
    LEVEL_LIST("level_list", "Показать доступные уровни"),
    TOPIC_LIST("topic_list", "Показать доступные темы"),
    START("start", "Начать обучение");

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
