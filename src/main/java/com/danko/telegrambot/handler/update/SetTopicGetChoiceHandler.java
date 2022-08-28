package com.danko.telegrambot.handler.update;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.Topic;
import com.danko.telegrambot.entity.User;
import com.danko.telegrambot.parser.InnerCommandParser;
import com.danko.telegrambot.service.LocalStorageTGService;
import com.danko.telegrambot.service.TopicService;
import com.danko.telegrambot.storage.UserAction;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class SetTopicGetChoiceHandler implements UpdateHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    private TopicService topicService;

    @Autowired
    private LocalStorageTGService localStorageTGService;

    @Autowired
    private InnerCommandParser innerCommandParser;

    @Override
    public boolean handleUpdate(Update update) throws TelegramApiException {
        if (!update.hasMessage()) {
            return false;
        }
        Message message = update.getMessage();
        if (!message.hasText()) {
            return false;
        }
        String text = message.getText();
        Optional<String> innerCommand = innerCommandParser.parseInnerCommand(text);
        if (!innerCommand.isPresent()) {
            return false;
        }

        if (!UpdateHandlerStage.COMMAND.isInUpdateHandlerStage(innerCommand.get())) {
            return false;
        }

        if (!getStage().name().equals(innerCommand.get().toUpperCase())){
            return false;
        }

        Long tgId = message.getChatId();
        Optional<User> optionalUser = localStorageTGService.getUser(tgId);

        if (!optionalUser.isPresent() || optionalUser.get().getLanguageLevel() == null) {
            sendMessage(tgId, "Для выбора темы, необходимо выбрать уровень языка. /level_list");
            return false;
        }

        sendChoice(update, optionalUser.get());
        return true;
    }

    @SneakyThrows
    private void sendChoice(Update update, User user) {
        List<Topic> topicList = topicService.getAllTopicByLanguageIdAndStatus(user.getLanguageLevel().getId(), true);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        topicList.forEach(topic ->
                buttons.add(
                        Arrays.asList(
                                InlineKeyboardButton.builder()
                                        .text("Выбрать Тему: " + topic.getName())
                                        .callbackData(topic.getName())
                                        .build()
                        )
                ));
        bot.execute(SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Выбор темы:")
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build());

        setNextUserAction(update.getMessage().getChatId());
    }

    private void sendMessage(Long chatId, String text) throws TelegramApiException {
        bot.execute(
                SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("html")
                        .text(text)
                        .build()
        );
    }

    private void setNextUserAction(Long tgId) {
        localStorageTGService.putNewUserAction(tgId, UserAction.SET_TOPIC);
    }

    @Override
    public UpdateHandlerStage getStage() {
        return UpdateHandlerStage.SET_TOPIC;
    }
}
