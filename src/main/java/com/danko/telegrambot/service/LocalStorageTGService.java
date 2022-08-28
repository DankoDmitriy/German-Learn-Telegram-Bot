package com.danko.telegrambot.service;

import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.storage.LocalStorageTG;
import com.danko.telegrambot.storage.UserAction;
import com.danko.telegrambot.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocalStorageTGService {
    private LocalStorageTG localStorageTG;
    private UserService userService;

    public void checkUserAndSetupUserAction(Long tgId) {
        if (!localStorageTG.checkSetupUserAndUserAction(tgId)) {
            Optional<User> optionalUser = userService.findUserByTgId(tgId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (UserUtil.checkUserProfileForCompletion(optionalUser.get())) {
                    putUserAndActionInLocalStorage(tgId, user, UserAction.REGISTRATION);
                } else {
                    putUserAndActionInLocalStorage(tgId, user, UserAction.PROFILE_FILLING);
                }
            } else {
                LocalDateTime localDateTime = LocalDateTime.now();
                User user = User.builder().tgId(tgId).created(localDateTime).isAdmin(false).update(localDateTime).build();
                user = userService.save(user);
                putUserAndActionInLocalStorage(tgId, user, UserAction.PROFILE_FILLING);
            }
        }
    }

    private void putUserAndActionInLocalStorage(Long tgId, User user, UserAction userAction) {
        localStorageTG.putUserInStorage(tgId, user);
        localStorageTG.putUserActionInStorage(tgId, userAction);
    }

    public void putNewUserAction(Long tgId, UserAction userAction) {
        localStorageTG.putUserActionInStorage(tgId, userAction);
    }

    public Optional<UserAction> getUserAction(Long tgId) {
        return localStorageTG.getUserAction(tgId);
    }

    public void putUserInStorage(Long tgId, User user) {
        localStorageTG.putUserInStorage(tgId, user);
    }

    public Optional<User> getUser(Long tgId) {
        return localStorageTG.getUser(tgId);
    }
}
