package com.danko.telegrambot.storage;

import com.danko.telegrambot.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class LocalStorageTG {
    private final Map<Long, User> activeUsersMap = new HashMap<>();
    private final Map<Long, UserAction> userActionMap = new HashMap<>();

    public void putUserInStorage(Long tgId, User user) {
        activeUsersMap.put(tgId, user);
    }

    public void putUserActionInStorage(Long tgId, UserAction userAction) {
        userActionMap.put(tgId, userAction);
    }

    public Optional<User> getUser(Long tgId) {
        if (activeUsersMap.containsKey(tgId)) {
            return Optional.of(activeUsersMap.get(tgId));
        } else {
            return Optional.empty();
        }
    }

    public Optional<UserAction> getUserAction(Long tgId) {
        if (userActionMap.containsKey(tgId)) {
            return Optional.of(userActionMap.get(tgId));
        } else {
            return Optional.empty();
        }
    }

    public boolean checkSetupUserAndUserAction(Long tgId) {
        return activeUsersMap.containsKey(tgId) && userActionMap.containsKey(tgId);
    }
}
