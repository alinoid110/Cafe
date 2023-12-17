package org.example.CoffeeBewerter.service.regFuncs;

import org.example.CoffeeBewerter.model.Review;
import org.example.CoffeeBewerter.model.UserToSupport;
import org.example.CoffeeBewerter.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.CafeFuncsConstants.INCORRECT_NUMBER_ANS;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.NEW_REVIEW_CREATED_LOG;
import static org.example.CoffeeBewerter.constants.logsConstants.LogsConstants.NEW_REVIEW_SAVED_LOG;
import static org.example.CoffeeBewerter.constants.regConstants.ReviewRegConstants.*;
import static org.example.CoffeeBewerter.constants.regConstants.CafeRegConstants.INCORRECT_CAFE_FORENAME;
import static org.example.CoffeeBewerter.constants.regConstants.CafeRegConstants.INCORRECT_CAFE_TYPE;
import static org.example.CoffeeBewerter.constants.regexConstants.regexConstants.*;

@Slf4j
@Component
public class ReviewRegistration {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserToSupportRepository userToSupportRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public String initializeRegistration(Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();

        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_REVIEW_REGISTRATION, chatId);

        Review review = new Review();
        UserToSupport userToSupport = new UserToSupport();

        userToSupport.setChatId(chatId);


        review.setOwnerId(chatId);
        review.setFullFilled(false);
        review.setCompleted(false);

        if (reviewRepository.findTopByOrderByReviewIdDesc() != null) {
            Long reviewId = reviewRepository.findTopByOrderByReviewIdDesc();
            review.setReviewId(reviewId + 1);
            userToSupport.setReviewId(reviewId + 1);
            sessionRepository.setReviewRegisterFunctionId(reviewId + 1, chatId);
        } else {
            Long reviewId = 1L;
            review.setReviewId(reviewId);
            userToSupport.setReviewId(reviewId);
            sessionRepository.setReviewRegisterFunctionId(reviewId, chatId);
        }

        if (userToSupportRepository.findTopByOrderByIdDesc() != null) {
            Long Id = userToSupportRepository.findTopByOrderByIdDesc();
            userToSupport.setId(Id + 1L);
        } else {
            Long Id = 1L;
            userToSupport.setId(Id);
        }

        reviewRepository.save(review);
        userToSupportRepository.save(userToSupport);

        log.info(NEW_REVIEW_SAVED_LOG + review);
        sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_TITLE, chatId);
        return NewReviewCommandReceived(chatId);

    }

    private static String NewReviewCommandReceived(long chatId) {
        log.info(NEW_REVIEW_CREATED_LOG + chatId);
        return SET_REVIEW_TITLE_TEXT;
    }


    public String continueRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        return switch (sessionRepository.findByChatId(chatId).getReviewRegisterFunctionContext()) {
            case (SET_REVIEW_TITLE) -> setReviewTitle(chatId, messageText);
            case (SET_REVIEW_DESCRIPTION) -> setReviewDescription(chatId, messageText);
            case (SET_REVIEW_PLACE) -> setReviewPlace(chatId, messageText);
            case (SET_REVIEW_EVENT_DATE) -> setReviewVisitDate(chatId, messageText);
            case (SET_REVIEW_USER_LIMIT) -> setReviewUserLimit(chatId, messageText);
            case (SET_REVIEW_CAFE_TYPE) -> setReviewCafeType(chatId, messageText);
            case (SET_REVIEW_FORENAME) -> setReviewForename(chatId, messageText);
            default -> INDEV_TEXT;
        };
    }

    public static boolean containsForbiddenWords(String text) {
        Pattern pattern = Pattern.compile(FORBIDDEN_WORDS_REGEX);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    private boolean isValidTitle(String name) { return !containsForbiddenWords(name.toLowerCase()); }
    private boolean isValidPlace(String name) {
        return  !name.toLowerCase().matches(FORBIDDEN_WORDS_REGEX) && name.toLowerCase().matches(PLACE_REGEX);
    }

    private boolean isValidDescription(String name) {
        return !name.toLowerCase().matches(FORBIDDEN_WORDS_REGEX);
    }

    public static boolean isValidName(String name) {
        return !containsForbiddenWords(name.toLowerCase());
    }

    private String setReviewTitle(long chatId, String messageText) {
        if (isValidTitle(messageText)) {
            reviewRepository.setTitleByOwnerIdAndReviewId(messageText, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_DESCRIPTION, chatId);
            return SET_REVIEW_DESCRIPTION_TEXT;
        } else {
            return INCORRECT_REVIEW_TITLE;
        }
    }

    private String setReviewDescription(long chatId, String messageText) {
        if (isValidDescription(messageText)) {
            reviewRepository.setDescriptionByOwnerIdAndReviewId(messageText, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_PLACE, chatId);
            return (SET_REVIEW_PLACE_TEXT);
        }else {
            return INCORRECT_REVIEW_DESCRIPTION;
        }
    }

    private String setReviewPlace(long chatId, String messageText) {
        if (isValidPlace(messageText)) {
            reviewRepository.setPlaceByOwnerIdAndReviewId(messageText, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_EVENT_DATE, chatId);
            return (SET_REVIEW_EVENT_DATE_TEXT);
        }else {
            return INCORRECT_REVIEW_PLACE;
        }
    }

    private String setReviewVisitDate(long chatId, String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STYLE);
        Date currentDate = new Date();
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (month > 12 || day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_REVIEW_REGISTRATION, chatId);
                sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_EVENT_DATE, chatId);
                return REVIEW_DATE_TEXT;
            }

            Timestamp eventDate = new Timestamp(parsedDate.getTime());
            reviewRepository.setVisitDateByOwnerIdAndReviewId(eventDate, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_USER_LIMIT, chatId);
            return SET_REVIEW_USER_LIMIT_TEXT;
        } catch (ParseException e) {
            e.printStackTrace();
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_REVIEW_REGISTRATION, chatId);
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_EVENT_DATE, chatId);
            return REVIEW_DATE_TEXT;
        }
    }


    private String setReviewUserLimit(long chatId, String messageText) {
        int userLimit;
        try {
            userLimit = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return INCORRECT_NUMBER_ANS;
        }
        reviewRepository.setUserLimitByOwnerIdAndReviewId(userLimit, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
        sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_CAFE_TYPE, chatId);
        return (SET_REVIEW_CAFE_TYPE_TEXT);
    }

    private String setReviewCafeType(long chatId, String messageText) {
        if (isValidName(messageText)) {
            reviewRepository.setCafeTypeByOwnerIdAndReviewId(messageText, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(SET_REVIEW_FORENAME, chatId);
            return (SET_REVIEW_FORENAME_TEXT);
        }else {
            return INCORRECT_CAFE_TYPE;
        }
    }

    private String setReviewForename(long chatId, String messageText) {
        if (isValidName(messageText)) {
            reviewRepository.setForenameByOwnerIdAndReviewId(messageText, chatId, sessionRepository.findByChatId(chatId).getReviewRegisterFunctionId());
            sessionRepository.setReviewRegisterFunctionContext(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setReviewRegisterFunctionId(0L, chatId);

            return (REGISTER_REVIEW_END_TEXT);
        }else {
            return INCORRECT_CAFE_FORENAME;
        }
    }

}
