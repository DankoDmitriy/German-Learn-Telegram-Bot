package com.danko.telegrambot.handler.update;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Topic;
import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.service.LocalStorageTGService;
import com.danko.telegrambot.service.TopicService;
import com.danko.telegrambot.service.UserService;
import com.danko.telegrambot.storage.UserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class SetTopicSetNewTopicHandler implements UpdateHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    private LocalStorageTGService localStorageTGService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Override
    public boolean handleUpdate(Update update) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery == null || callbackQuery.getData() == null) {
            return false;
        }

        Long tgId = callbackQuery.getMessage().getChatId();
        Optional<UserAction> optionalUserAction = localStorageTGService.getUserAction(tgId);
        if (!optionalUserAction.isPresent()) {
            return false;
        }

        if (!optionalUserAction.get().equals(UserAction.SET_TOPIC)) {
            return false;
        }

        if (!topicService.checkTopic(callbackQuery.getData(), true)) {
            return false;
        }

        return changeTopic(tgId, callbackQuery);
    }

    private boolean changeTopic(Long tgId, CallbackQuery callbackQuery) throws TelegramApiException {
        Optional<User> optionalUser = userService.findUserByTgId(tgId);
        if (!optionalUser.isPresent()) {
            return false;
        }

        Optional<Topic> topicOptional = topicService.getTopicByNameAndStatus(callbackQuery.getData(), true);

        if (!topicOptional.isPresent()) {
            return false;
        }

        User user = optionalUser.get();
        user.setTopic(topicOptional.get());

        userService.save(user);

        localStorageTGService.putNewUserAction(tgId, UserAction.REGISTRATION);
        localStorageTGService.putUserInStorage(tgId, user);

        sendMessage(tgId);

        return true;
    }

    private void sendMessage(Long tgId) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(tgId)
                        .parseMode("html")
                        .text(
                                "Тема выбрана.\n\n"
                                        + "Теперь вы можете начать обучение.\n"
                        )
                        .build());
    }

    @Override
    public UpdateHandlerStage getStage() {
        return UpdateHandlerStage.SET_TOPIC;
    }
}
