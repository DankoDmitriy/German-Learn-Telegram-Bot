package com.danko.telegrambot.parser;

import java.util.Optional;

public interface InnerCommandParser {

    Optional<String> parseInnerCommand(String message);
}
