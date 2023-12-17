package org.example.CoffeeBewerter.service.regFuncs;

import org.example.CoffeeBewerter.model.Cafe;
import org.example.CoffeeBewerter.repository.CafeRepository;
import org.example.CoffeeBewerter.repository.SessionRepository;
import org.example.CoffeeBewerter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.SpecialText.GLOBAL_CONTEXT_DEFAULT;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.NEW_CAFE_CREATED_LOG;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.NEW_CAFE_SAVED_LOG;
import static org.example.CoffeeBewerter.constants.regConstants.CafeRegConstants.*;
import static org.example.CoffeeBewerter.constants.regConstants.UserRegConstants.*;
import static org.example.CoffeeBewerter.constants.regexConstants.regexConstants.NAME_REGEX;

@Slf4j
@Component
public class CafeRegistration {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public String initializeRegistration(Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();

        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_CAFE_REGISTRATION, chatId);

        Cafe cafe = new Cafe();

        cafe.setOwnerId(chatId);

        if (cafeRepository.findTopByOrderByOwnerIdDesc(chatId) != null) {
            cafe.setCafeId(cafeRepository.findTopByOrderByOwnerIdDesc(chatId) + 1);
        } else {
            Long cafeId = 1L;
            cafe.setCafeId(cafeId);
        }

        cafeRepository.save(cafe);

        log.info(NEW_CAFE_SAVED_LOG + cafe);
        sessionRepository.setCafeRegisterFunctionContext(SET_CAFE_NAME, chatId);
        return NewCafeCommandReceived(chatId);

    }

    private static String NewCafeCommandReceived(long chatId) {
        log.info(NEW_CAFE_CREATED_LOG + chatId);
        return SET_CAFE_NAME_DOP_TEXT;
    }


    public String continueRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        return switch (sessionRepository.findByChatId(chatId).getCafeRegisterFunctionContext()) {
            case (SET_CAFE_NAME) -> SetCafeName(chatId, messageText);
            case (REGISTER_CAFE_TYPE_VANUE) -> SetCafeType(chatId, messageText);
            case (REGISTER_CAFE_FORENAME) -> SetCafeForename(chatId, messageText);
            case (REGISTER_CAFE_PHOTO) -> SetCafePhoto(chatId, update);
            default -> INDEV_TEXT;
        };
    }


    private boolean isValidName(String name) {
        return name.matches(NAME_REGEX);
    }

    private String SetCafeForename(long chatId, String messageText) {
        if (isValidName(messageText)) {
            cafeRepository.setCafeForenameByOwnerIdAndCafeId(messageText, chatId, cafeRepository.findTopByOrderByOwnerIdDesc(chatId));
            sessionRepository.setCafeRegisterFunctionContext(REGISTER_CAFE_PHOTO, chatId);
            return (REGISTER_CAFE_PHOTO_TEXT);
        } else {
            return INCORRECT_CAFE_FORENAME;
        }
    }

    private String SetCafePhoto(Long chatId, Update update) {
        if (update.getMessage().hasPhoto()) {
            sessionRepository.setCafeRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return (REGISTER_CAFE_ENDED_TEXT);
        } else {
            return INCORRECT_CAFE_PHOTO;
        }
    }

    private String SetCafeType(long chatId, String messageText) {
        if (isValidName(messageText)) {
            cafeRepository.setCafeTypeByOwnerIdAndCafeId(messageText, chatId, cafeRepository.findTopByOrderByOwnerIdDesc(chatId));
            sessionRepository.setCafeRegisterFunctionContext(REGISTER_CAFE_FORENAME, chatId);
            return (REGISTER_CAFE_FORENAME_TEXT);
        } else {
            return INCORRECT_CAFE_TYPE;
        }
    }

    private String SetCafeName(long chatId, String messageText) {
        if (isValidName(messageText)) {
            cafeRepository.setCafeNameByOwnerIdAndCafeId(messageText, chatId, cafeRepository.findTopByOrderByOwnerIdDesc(chatId));
            sessionRepository.setCafeRegisterFunctionContext(REGISTER_CAFE_TYPE_VANUE, chatId);
            return (REGISTER_CAFE_TYPE_VENUE_TEXT);
        } else {
            return INCORRECT_CAFE_DOP_NAME;
        }
    }


}

