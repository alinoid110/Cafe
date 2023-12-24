package org.example.CoffeeBewerter.service.modelsConnectedFuncs;

import org.example.CoffeeBewerter.ListMenus;
import org.example.CoffeeBewerter.repository.*;
import org.example.CoffeeBewerter.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.constants.keyboardsConstants.ListMenusConstants.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.UserFuncsConstants.*;
import static org.example.CoffeeBewerter.constants.regConstants.UserRegConstants.*;
import static org.example.CoffeeBewerter.constants.regexConstants.regexConstants.NAME_REGEX;
import static org.example.CoffeeBewerter.constants.regexConstants.regexConstants.PHONE_REGEX;

@Slf4j
@Component
public class UserFuncs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserToSupportRepository userToSupportRepository;

    public SendMessage checkExistingProfile(long chatId) {

        if (userRepository.findById(chatId).isEmpty()) {
            SendMessage message = new SendMessage();
            message.setText(NO_CREATED_PROFILE_TEXT);
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.registrationKeyboard());

            return message;
        } else {
            return null;
        }

    }

    public String showProfile(long chatId) {

        if (userRepository.findById(chatId).isEmpty()) {
            return null;
        }

        User user = userRepository.findByChatId(chatId);

        String profileInfo = USER_ID_FOR_MSG + user.getChatId() + "\n" +
                USER_LOGIN_FOR_MSG + user.getUserName() + "\n" +
                USER_NAME_FOR_MSG + user.getFirstName() + "\n" +
                USER_LAST_NAME_FOR_MSG + user.getLastName() + "\n" +
                USER_PHONE_NUMBER_FOR_MSG + user.getPhoneNumber() + "\n" +
                USER_REG_DATE_FOR_MSG + user.getRegisteredAt() + "\n\n";

        return profileInfo;

    }

    public String deleteProfileQuestion() {
        return DELETE_PROFILE_QUESTION_TEXT;
    }


    public String deleteProfile(Long chatId) {
        userRepository.deleteByChatId(chatId);
        cafeRepository.deleteByOwnerId(chatId);
        sessionRepository.deleteByChatId(chatId);
        reviewRepository.deleteAllByOwnerId(chatId);
        userToSupportRepository.deleteAllByChatId(chatId);
        return DELETE_PROFILE_TEXT;
    }


    public String editProfile(long chatId, String editContext) {

        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_USER_EDIT, chatId);

        switch (editContext) {
            case EDIT_PROFILE_NAME:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_NAME, chatId);

                return GIVE_NAME_TEXT;

            case EDIT_PROFILE_LASTNAME:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_LASTNAME, chatId);
                return GIVE_LAST_NAME_TEXT;

            case EDIT_PROFILE_PHONE_NUMBER:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_PHONE_NUMBER, chatId);
                return GIVE_PHONE_NUMBER_TEXT;
            default:
                return INDEV_TEXT;

        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_REGEX);
    }

    private boolean isValidName(String name) {
        return name.matches(NAME_REGEX);
    }

    public String editProfileAction(long chatId, String messageText) {
        switch (sessionRepository.findByChatId(chatId).getEditFunctionContext()) {
            case EDIT_PROFILE_NAME:
                if (isValidName(messageText)) {
                    userRepository.setFirstNameByChatId(messageText, chatId);
                    sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    return CHANGED_NAME_TEXT;
                } else {
                    return INCORRECT_FIRST_NAME;
                }

            case EDIT_PROFILE_LASTNAME:
                if (isValidName(messageText)) {
                    userRepository.setLastNameByChatId(messageText, chatId);
                    sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    return CHANGED_LAST_NAME_TEXT;
                } else {
                    return INCORRECT_LAST_NAME;
                }

            case EDIT_PROFILE_PHONE_NUMBER:
                if (isValidPhoneNumber(messageText)) {
                    userRepository.setPhoneNumberByChatId(messageText, chatId);
                    sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    return CHANGED_PHONE_TEXT;
                } else {
                    return INCORRECT_PHONE_NUMBER;
                }
            default:
                return INDEV_TEXT;

        }
    }
}
