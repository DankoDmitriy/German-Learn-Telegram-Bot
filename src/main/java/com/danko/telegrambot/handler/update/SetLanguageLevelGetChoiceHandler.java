package com.danko.telegrambot.handler.update;

import com.danko.telegrambot.bot.LearnBot;
import com.danko.telegrambot.entity.LanguageLevel;
import com.danko.telegrambot.parser.InnerCommandParser;
import com.danko.telegrambot.service.LanguageLevelService;
import com.danko.telegrambot.service.LocalStorageTGService;
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
public class SetLanguageLevelGetChoiceHandler implements UpdateHandler {

    @Autowired
    @Lazy
    private LearnBot bot;

    @Autowired
    private InnerCommandParser innerCommandParser;

    @Autowired
    private LanguageLevelService languageLevelService;

    @Autowired
    private LocalStorageTGService localStorageTGService;

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

        sendChoice(update);

        return true;
    }

    @SneakyThrows
    private void sendChoice(Update update) {
        List<LanguageLevel> languageLevels = languageLevelService.getActive();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        languageLevels.forEach(languageLevel ->
                buttons.add(
                        Arrays.asList(
                                InlineKeyboardButton.builder()
                                        .text("Выбрать уровень: " + languageLevel.getName())
                                        .callbackData(languageLevel.getName())
                                        .build()
                        )
                ));
        bot.execute(SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Выбор уровня языка:")
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build());

        setNextUserAction(update.getMessage().getChatId());
    }

    private void setNextUserAction(Long tgId) {
        localStorageTGService.putNewUserAction(tgId, UserAction.SET_LANGUAGE_LEVEL);
    }

    @Override
    public UpdateHandlerStage getStage() {
        return UpdateHandlerStage.SET_LANGUAGE_LEVEL;
    }
}

