package com.danko.telegrambot.handler.update;

import java.util.Locale;

public enum UpdateHandlerStage {
    SET_LANGUAGE_LEVEL,
    SET_TOPIC,
    COMMAND;

    public int getOrder() {
        return ordinal();
    }

    public boolean isInUpdateHandlerStage(String s) {
        try {
            Enum.valueOf(UpdateHandlerStage.class, s.toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
