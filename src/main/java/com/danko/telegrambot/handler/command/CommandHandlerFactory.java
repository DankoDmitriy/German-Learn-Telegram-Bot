package com.danko.telegrambot.handler.command;

import com.danko.telegrambot.entity.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandHandlerFactory {
    @Autowired
    private List<CommandHandler> handlers;
    private Map<Command, CommandHandler> map;

    @PostConstruct
    private void init() {
        map = new HashMap<>();
        handlers.forEach(h -> map.put(h.getCommand(), h));
    }

    public CommandHandler getHandler(Command command) {
        return Optional.ofNullable(map.get(command))
                .orElseThrow(() -> new IllegalStateException("Not supported command: " + command));
    }
}
