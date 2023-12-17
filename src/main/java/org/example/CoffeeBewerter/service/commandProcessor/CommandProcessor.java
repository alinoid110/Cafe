package org.example.CoffeeBewerter.service.commandProcessor;

import lombok.extern.slf4j.Slf4j;
import org.example.CoffeeBewerter.service.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.UserFuncsConstants.EDIT_CHOISE;
import static org.example.CoffeeBewerter.constants.keyboardsConstants.CommandListConstants.*;
import static org.example.CoffeeBewerter.constants.keyboardsConstants.KeyboardMenusConstants.*;
import static org.example.CoffeeBewerter.constants.keyboardsConstants.ListMenusConstants.*;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.ERROR_GLOBAL_CONTEXT_OCCURRED;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.ERROR_OCCURRED;
import static org.example.CoffeeBewerter.constants.regConstants.CafeRegConstants.CANCEL_OPERATION;
import static org.example.CoffeeBewerter.constants.regConstants.CafeRegConstants.REGISTER_CAFE_PHOTO;

@Slf4j
@Component
public class CommandProcessor {
    private Long chatId = 0L;
    private Long messageId = 0L;
    private Update update = new Update();
    private final TelegramBot bot;

    public CommandProcessor(TelegramBot bot) {
        this.bot = bot;
    }

    public void processCallBackCommand(String callBackData, Long chatId, Long messageId, Update update) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.update = update;

        Map<String, Runnable> commandMap = callBackCommandMap();

        Runnable defaultAction = () -> bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());

        commandMap.getOrDefault(callBackData, defaultAction).run();
    }

    private Map<String, Runnable> callBackCommandMap() {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(DELETE_PROFILE_QUESTION, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.deleteProfileQuestion(), bot.listMenus.profileDeleteKeyboard()));
        commandMap.put(DELETE_PROFILE_CONFIRM, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.deleteProfile(chatId)));
        commandMap.put(DELETE_PROFILE_DENY, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.showProfile(chatId), bot.listMenus.profileButtonKeyboard()));
        commandMap.put(EDIT_PROFILE, () ->
                bot.sendEditMessage(chatId, messageId, EDIT_CHOISE, bot.listMenus.profileEditKeyboard()));
        commandMap.put(EDIT_PROFILE_NAME, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_NAME)));
        commandMap.put(EDIT_PROFILE_LASTNAME, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_LASTNAME)));
        commandMap.put(EDIT_PROFILE_PHONE_NUMBER, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_PHONE_NUMBER)));
        commandMap.put(CAFE_QUESTION_CONFIRM, () ->
                bot.sendEditMessage(chatId, messageId, bot.cafeRegistration.initializeRegistration(update)));
        commandMap.put(CAFE_ADD, () ->
                bot.sendEditMessage(chatId, messageId, bot.cafeRegistration.initializeRegistration(update)));
        commandMap.put(CAFE_DELETE_SELECTION, () ->
                bot.sendMessage(chatId, bot.cafeFuncs.deleteCafesSelection(chatId)));
        commandMap.put(CAFE_QUESTION_DENY, () ->
                bot.sendEditMessage(chatId, messageId, CANCEL_OPERATION));
        commandMap.put(REVIEW_ADD, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewRegistration.initializeRegistration(update)));
        commandMap.put(CREATED_REVIEW_FULL_INFO_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.fullInfoReviewSelection(chatId)));
        commandMap.put(APPLIED_REVIEW_FULL_INFO_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.fullInfoReviewSelection(chatId)));
        commandMap.put(REVIEW_DELETE_BUTTON_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.deleteReviewSelection(chatId)));
        commandMap.put(REVIEW_EDIT_BUTTON_SELECT, () ->
                bot.sendMessage(chatId, bot.reviewFuncs.editReviewSelectionNumber(chatId)));
        commandMap.put(EDIT_REVIEW_BUTTON_TITLE, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_TITLE)));
        commandMap.put(EDIT_REVIEW_BUTTON_PLACE, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_PLACE)));
        commandMap.put(EDIT_REVIEW_BUTTON_CAFE_TYPE, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_CAFE_TYPE)));
        commandMap.put(EDIT_REVIEW_BUTTON_FORENAME, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_FORENAME)));
        commandMap.put(EDIT_REVIEW_BUTTON_DATE, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_DATE)));
        commandMap.put(EDIT_REVIEW_BUTTON_DESCRIPTION, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_DESCRIPTION)));
        commandMap.put(EDIT_REVIEW_BUTTON_LIMIT, () ->
                bot.sendEditMessage(chatId, messageId, bot.reviewFuncs.editReview(chatId, EDIT_REVIEW_BUTTON_LIMIT)));
        commandMap.put(REGISTRATION, () ->
                bot.sendMessage(chatId, bot.userRegistration.initializeRegistration(update)));
        commandMap.put(LIKE_REVIEW, () ->
                bot.sendMessage(chatId, bot.searchReview.likeReview(chatId)));
        commandMap.put(DISLIKE_REVIEW, () ->
                bot.sendMessage(chatId, bot.searchReview.searchReview(chatId)));


        return commandMap;
    }

    public void processTextCommandDefaultContext(String messageText, Long chatId, Long messageId, Update update) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.update = update;

        Map<String, Runnable> commandMap = textCommandDefaultContextMap();

        Runnable defaultAction = () -> bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());

        commandMap.getOrDefault(messageText, defaultAction).run();
    }

    private Map<String, Runnable> textCommandDefaultContextMap() {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(ADMIN_SEND_BROADCAST, () -> {
            if (adminsIdList.contains(chatId)) {
                bot.sessionRepository.setGlobalContextByChatId(GLOBAL_ADMIN_BROADCAST, chatId);
                bot.sendMessage(chatId, ADMIN_INPUT_BROADCAST_MESSAGE);
            } else {
                bot.sendMessage(chatId, NOT_AN_ADMIN);
            }
        });
        commandMap.put(START_MENU_COMMAND, () ->
                bot.sendMessage(chatId, bot.generalFuncs.startCommandReceived(chatId, update.getMessage().getChat().getFirstName()), bot.keyboardMenus.mainKeyboard()));
        commandMap.put(HELP_MENU_COMMAND, () ->
                bot.sendMessage(chatId, HELP_TEXT, bot.keyboardMenus.mainKeyboard()));
        commandMap.put(REGISTER_MENU_COMMAND, () ->
                bot.sendMessage(chatId, bot.userRegistration.initializeRegistration(update)));
        commandMap.put(CAFE, () ->
                bot.sendMessage(chatId, bot.cafeFuncs.showCafes(chatId)));
        commandMap.put(MY_REVIEW, () -> {
                bot.sendMessage(chatId, bot.reviewFuncs.changeToMyReview(chatId));
                bot.sendMessage(chatId, bot.reviewFuncs.showMyReview(chatId));
        });
        commandMap.put(MY_REVIEW_CREATED, () ->
                bot.sendMessage(chatId, bot.reviewFuncs.showCreatedReview(chatId)));
        commandMap.put(MY_REVIEW_SUPPORT, () ->
                bot.sendMessage(chatId, bot.reviewFuncs.showAppliedReview(chatId)));
        commandMap.put(SEARCH_REVIEW, () -> {
            bot.sendMessage(chatId, bot.searchReview.searchReview(chatId));
            try {
                bot.sendPhoto(chatId, bot.searchReview.showRandomCafe(), PHOTO_TO_YOU);
            } catch (TelegramApiException | IOException e) {
                log.error(ERROR_OCCURRED + e.getMessage());
            }
        });
        commandMap.put(BACK, () ->
                bot.sendMessage(chatId, bot.reviewFuncs.changeToMainMenu(chatId)));
        commandMap.put(PROFILE, () -> {
            bot.sendMessage(chatId, bot.userFuncs.checkExistingProfile(chatId));
            bot.sendMessage(chatId, bot.userFuncs.showProfile(chatId), bot.listMenus.profileButtonKeyboard());
        });

        return commandMap;
    }

    public void processGlobalContextCommand(String globalFunctionContext, String messageText, Long _messageId, Long _chatId, Update _update) {
        this.chatId = _chatId;
        this.messageId = _messageId;
        this.update = _update;

        Map<String, Runnable> commandMap = globalContextCommandMap(messageText);

        Runnable defaultAction = () -> {
            bot.sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            log.error(ERROR_GLOBAL_CONTEXT_OCCURRED + update.getMessage().getText());
            bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());
        };

        commandMap.getOrDefault(globalFunctionContext, defaultAction).run();
    }

    private Map<String, Runnable> globalContextCommandMap(String messageText) {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(GLOBAL_ADMIN_BROADCAST, () -> bot.adminBroadcastNews(messageText, chatId));
        commandMap.put(GLOBAL_CONTEXT_USER_REGISTRATION, () -> bot.sendMessage(chatId, bot.userRegistration.continueRegistration(update)));
        commandMap.put(GLOBAL_CONTEXT_CAFE_REGISTRATION, () -> {
            if (update.getMessage().hasPhoto() && Objects.equals(bot.sessionRepository.findByChatId(chatId).getCafeRegisterFunctionContext(), REGISTER_CAFE_PHOTO)) {
                bot.downloadImage(update, bot.cafeRepository.findTopByOrderByOwnerIdDesc(chatId));
            }
            bot.sendMessage(chatId, bot.cafeRegistration.continueRegistration(update));
        });
        commandMap.put(GLOBAL_CONTEXT_USER_EDIT, () -> bot.sendMessage(chatId, bot.userFuncs.editProfileAction(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_CAFE_DELETE, () -> bot.sendMessage(chatId, bot.cafeFuncs.deleteCafes(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_FULL_REVIEW_INFO, () -> bot.sendMessage(chatId, bot.reviewFuncs.fullInfoReviewByNumber(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_REVIEW_DELETE, () -> bot.sendMessage(chatId, bot.reviewFuncs.deleteReview(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_REVIEW_REGISTRATION, () -> bot.sendMessage(chatId, bot.reviewRegistration.continueRegistration(update)));
        commandMap.put(GLOBAL_CONTEXT_REVIEW_EDIT, () -> bot.sendMessage(chatId, bot.reviewFuncs.functionEditReview(chatId, messageText)));

        return commandMap;
    }


}
