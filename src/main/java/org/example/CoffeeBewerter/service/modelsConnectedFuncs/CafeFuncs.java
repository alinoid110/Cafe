package org.example.CoffeeBewerter.service.modelsConnectedFuncs;

import lombok.extern.slf4j.Slf4j;
import org.example.CoffeeBewerter.ListMenus;
import org.example.CoffeeBewerter.model.Cafe;
import org.example.CoffeeBewerter.repository.CafeRepository;
import org.example.CoffeeBewerter.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.CafeFuncsConstants.*;

@Slf4j
@Component
public class CafeFuncs {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;
    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private UserFuncs userFuncs;

    public SendMessage showCafes(Long chatId) {

        if (userFuncs.checkExistingProfile(chatId) != null){
            return userFuncs.checkExistingProfile(chatId);
        }


        ArrayList<Cafe> cafes = cafeRepository.findAllByOwnerId(chatId);

        if (cafes.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setText(NO_CAFES_TEXT);
            message.setReplyMarkup(listMenus.cafeQuestionButtonKeyboard());
            return message;
        } else {
            StringBuilder cafe_info_message = new StringBuilder();

            for (Cafe cafe : cafes) {
                cafe_info_message.append(CAFE_ID_FOR_MSG).append(cafe.getCafeId()).append("\n")
                        .append(CAFE_FIRSTNAME_FOR_MSG).append(cafe.getCafeNote()).append("\n")
                        .append(CAFE_TYPE_FOR_MSG).append(cafe.getCafeType()).append("\n")
                        .append(CAFE_FORENAME_FOR_MSG).append(cafe.getCafeForename()).append("\n")
                        .append(CAFE_VISITOR_ID_FOR_MSG).append(cafe.getOwnerId()).append("\n\n");
            }

            SendMessage message = new SendMessage();
            message.setText(cafe_info_message.toString());
            message.setReplyMarkup(listMenus.cafeAddButtonKeyboard());

            return message;

        }

    }

    public String deleteCafesSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_CAFE_DELETE, chatId);
        return DELETE_CAFE_SELECT_TEXT;
    }

    public String deleteCafes(Long chatId, String getFromMsg) {
        Long cafeId;
        try {
            cafeId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (cafeRepository.findByCafeIdAndOwnerId(cafeId, chatId) != null) {
            cafeRepository.deleteByOwnerIdAndCafeId(chatId, cafeId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return DELETE_CAFE_TEXT;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_CAFE_NUMBER_ANS;
        }
    }


}
