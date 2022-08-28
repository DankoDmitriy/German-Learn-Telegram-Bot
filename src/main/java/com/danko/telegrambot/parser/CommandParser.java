package com.danko.telegrambot.parser;

import com.danko.telegrambot.entity.Command;

import java.util.Optional;

public interface CommandParser {
    Optional<Command> parseCommand(String message);
}
