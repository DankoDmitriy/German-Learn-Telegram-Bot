package com.danko.telegrambot.util;

import com.danko.telegrambot.entity.User;

public class UserUtil {
    public static boolean checkUserProfileForCompletion(User user) {
        if (user.getLanguageLevel() == null ||
                user.getTopic() == null ||
                user.getLanguageLevel().getStatus() ||
                user.getTopic().getStatus()) {
            return false;
        } else {
            return true;
        }
    }
}
