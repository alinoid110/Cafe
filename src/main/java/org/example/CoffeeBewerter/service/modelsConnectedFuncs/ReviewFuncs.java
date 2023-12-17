package org.example.CoffeeBewerter.service.modelsConnectedFuncs;

import lombok.extern.slf4j.Slf4j;
import org.example.CoffeeBewerter.KeyboardMenus;
import org.example.CoffeeBewerter.ListMenus;
import org.example.CoffeeBewerter.model.Review;
import org.example.CoffeeBewerter.repository.ReviewRepository;
import org.example.CoffeeBewerter.repository.SessionRepository;
import org.example.CoffeeBewerter.repository.UserRepository;
import org.example.CoffeeBewerter.repository.UserToSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.example.CoffeeBewerter.SpecialText.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.ReviewFuncsConstants.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.CafeFuncsConstants.INCORRECT_NUMBER_ANS;
import static org.example.CoffeeBewerter.constants.keyboardsConstants.ListMenusConstants.*;
import static org.example.CoffeeBewerter.constants.regConstants.ReviewRegConstants.*;
import static org.example.CoffeeBewerter.constants.regexConstants.regexConstants.DATE_FORMAT_STYLE;
import static org.example.CoffeeBewerter.service.regFuncs.ReviewRegistration.isValidName;


@Slf4j
@Component
public class ReviewFuncs {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;

    @Autowired
    private KeyboardMenus keyboardMenus;

    @Autowired
    private UserFuncs userFuncs;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserToSupportRepository userToSupportRepository;
    @Autowired
    private UserRepository userRepository;

    public ArrayList<Review> getMyCreatedReview(Long chatId) {
        return reviewRepository.findAllByOwnerId(chatId);
    }

    public ArrayList<Review> getMyAppliedReview(Long chatId) {
        return reviewRepository.findMyAppliedReview(chatId);
    }

    public SendMessage showMyReview(Long chatId) {

        if (userRepository.findById(chatId).isEmpty()) {
            return null;
        }

        ArrayList<Review> my_review_created = getMyCreatedReview(chatId);
        ArrayList<Review> my_review_applied = getMyAppliedReview(chatId);

        SendMessage message = new SendMessage();
        if (my_review_created.isEmpty() && my_review_applied.isEmpty()) {
            message.setText(NO_REVIEW_TEXT);
            message.setReplyMarkup(listMenus.reviewKeyboard());
        } else {
            message.setText(SELECT_REVIEW_TYPE_TEXT);
        }
        return message;

    }

    public SendMessage changeToMyReview(Long chatId) {
        if (userFuncs.checkExistingProfile(chatId) != null) {
            return userFuncs.checkExistingProfile(chatId);
        }

        ArrayList<Review> my_review_created = getMyCreatedReview(chatId);
        ArrayList<Review> my_review_applied = getMyAppliedReview(chatId);
        SendMessage message = new SendMessage();

        if (my_review_created.isEmpty() && my_review_applied.isEmpty()) {
            return null;
        } else {
            message.setText(CHANGE_TO_MY_REVIEW_TEXT);
            message.setChatId(chatId);
            message.setReplyMarkup(keyboardMenus.myReviewKeyboard());
        }

        return message;
    }


    public SendMessage changeToMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setText(CHANGE_TO_MAIN_MENU);
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMenus.mainKeyboard());
        return message;
    }

    public String showMainReviewInfo(Review review) {
        String created_review_item = REVIEW_ID_FOR_MSG + review.getReviewId() + "\n" +
                REVIEW_NAME_FOR_MSG + review.getTitle() + "\n" +
                REVIEW_DATE_FOR_MSG + review.getVisitDate() + "\n" +
                REVIEW_CAFE_TYPE_FOR_MSG + review.getCafeType() + "\n" +
                REVIEW_CAFE_FORENAME_FOR_MSG + review.getForename() + "\n\n";
        return created_review_item;
    }

    public String showFullReviewInfo(Review review) {
        String created_review_item = REVIEW_ID_FOR_MSG + review.getReviewId() + "\n" +
                REVIEW_NAME_FOR_MSG + review.getTitle() + "\n" +
                REVIEW_DATE_FOR_MSG + review.getVisitDate() + "\n" +
                REVIEW_PLACE_FOR_MSG + review.getPlace() + "\n" +
                REVIEW_CAFE_TYPE_FOR_MSG + review.getCafeType() + "\n" +
                REVIEW_CAFE_FORENAME_FOR_MSG + review.getForename() + "\n" +
                REVIEW_DESCRIPTION_FOR_MSG + review.getDescription() + "\n" +
                REVIEW_MAX_PARTICIPANTS_AMOUNT_FOR_MSG + review.getUserLimit() + "\n" +
                REVIEW_SUPPORT_AMOUNT_FOR_MSG + userToSupportRepository.countByReviewId(review.getReviewId()) + "\n\n";


        return created_review_item;
    }


    public SendMessage showCreatedReview(long chatId) {
        ArrayList<Review> my_review_created = getMyCreatedReview(chatId);
        SendMessage message = new SendMessage();
        if (my_review_created.isEmpty()) {
            message.setText(NO_CREATED_REVIEW_TEXT);
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.reviewKeyboard());
        } else {
            StringBuilder created_review_list = new StringBuilder();

            for (Review review : my_review_created) {
                created_review_list.append(showMainReviewInfo(review));
            }

            message.setText(created_review_list.toString());
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.createdReviewKeyboard());
        }
        return message;
    }

    public SendMessage showAppliedReview(long chatId) {
        ArrayList<Review> my_review_applied = getMyAppliedReview(chatId);

        SendMessage message = new SendMessage();
        if (my_review_applied.isEmpty()) {
            message.setText(NO_APPLIED_REVIEW_TEXT);
            message.setChatId(chatId);
        } else {
            StringBuilder applied_review_list = new StringBuilder();

            for (Review review : my_review_applied) {
                applied_review_list.append(showMainReviewInfo(review));
            }

            message.setText(applied_review_list.toString());
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.appliedReviewKeyboard());
        }

        return message;

    }


    public String fullInfoReviewSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_FULL_REVIEW_INFO, chatId);
        return SELECT_FULL_REVIEW_INFO_TEXT;
    }

    public String fullInfoReviewByNumber(Long chatId, String getFromMsg) {
        Long reviewId;
        try {
            reviewId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (reviewRepository.findByReviewIdAndOwnerId(reviewId, chatId) != null) {
            String fullReviewInfo = showFullReviewInfo(reviewRepository.findByReviewId(reviewId));
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return fullReviewInfo;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_FULL_INFO_NUMBER_ANS;
        }
    }

    public String deleteReviewSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_REVIEW_DELETE, chatId);
        return SELECT_REVIEW_DELETE_TEXT;
    }

    public String deleteReview(Long chatId, String getFromMsg) {
        Long reviewId;
        try {
            reviewId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (reviewRepository.findByReviewIdAndOwnerId(reviewId, chatId) != null) {
            userToSupportRepository.deleteAllByReviewId(reviewId);
            reviewRepository.deleteAllByReviewId(reviewId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return REVIEW_DELETE_TEXT;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_FULL_INFO_NUMBER_ANS;
        }
    }


    public String editReviewSelectionNumber(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_REVIEW_EDIT, chatId);
        sessionRepository.setEditReviewFunctionContextByChatId(SELECT_NAME_FIELD_REVIEW_EDIT_CONTEXT, chatId);
        return SELECT_NUMBER_REVIEW_EDIT_TEXT;
    }


    public SendMessage functionEditReview(Long chatId, String messageText) {
        if (Objects.equals(sessionRepository.findByChatId(chatId).getEditReviewFunctionContext(), SELECT_NAME_FIELD_REVIEW_EDIT_CONTEXT)) {
            return editReviewNameFieldText(chatId, messageText);
        }
        return editReviewAction(chatId, messageText);
    }

    public SendMessage editReviewNameFieldText(Long chatId, String getFromMsg) {
        Long reviewId;
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        try {
            reviewId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            message.setText(INCORRECT_NUMBER_ANS);
            return message;
        }

        if (reviewRepository.findByReviewIdAndOwnerId(reviewId, chatId) != null) {

            sessionRepository.setNumberEditReviewByChatId(reviewId, chatId);
            message.setChatId(chatId);
            message.setText(SELECT_NAME_FIELD_REVIEW_EDIT_TEXT);
            message.setReplyMarkup(listMenus.reviewEditKeyboard());
            return message;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            message.setText(INCORRECT_FULL_INFO_NUMBER_ANS);
            return message;
        }
    }

    public String editReview(Long chatId, String editContext) {
        switch (editContext) {
            case EDIT_REVIEW_BUTTON_CAFE_TYPE:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_CAFE_TYPE, chatId);
                return SET_REVIEW_CAFE_TYPE_TEXT;

            case EDIT_REVIEW_BUTTON_FORENAME:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_FORENAME, chatId);
                return SET_REVIEW_FORENAME_TEXT;

            case EDIT_REVIEW_BUTTON_DATE:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_DATE, chatId);
                return SET_REVIEW_EVENT_DATE_TEXT;

            case EDIT_REVIEW_BUTTON_DESCRIPTION:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_DESCRIPTION, chatId);

                return SET_REVIEW_DESCRIPTION_TEXT;

            case EDIT_REVIEW_BUTTON_PLACE:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_PLACE, chatId);
                return SET_REVIEW_PLACE_TEXT;

            case EDIT_REVIEW_BUTTON_TITLE:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_TITLE, chatId);
                return SET_REVIEW_TITLE_TEXT;
            case EDIT_REVIEW_BUTTON_LIMIT:
                sessionRepository.setEditReviewFunctionContextByChatId(EDIT_REVIEW_BUTTON_LIMIT, chatId);
                return SET_REVIEW_USER_LIMIT_TEXT;
            default:
                return INDEV_TEXT;
        }
    }

    public SendMessage editReviewAction(Long chatId, String messageText) {
        Long reviewId = sessionRepository.findByChatId(chatId).getNumberEditReview();
        SendMessage message = new SendMessage();
        switch (sessionRepository.findByChatId(chatId).getEditReviewFunctionContext()) {
            case EDIT_REVIEW_BUTTON_CAFE_TYPE:
                message.setText(setReviewCafeType(chatId, messageText, reviewId));
                return message;

            case EDIT_REVIEW_BUTTON_FORENAME:
                message.setText(setReviewForename(chatId, messageText, reviewId));
                return message;

            case EDIT_REVIEW_BUTTON_DATE:
                System.out.println(EDIT_REVIEW_BUTTON_DATE);
                message.setText(setReviewData(chatId, messageText, reviewId));
                return message;

            case EDIT_REVIEW_BUTTON_DESCRIPTION:
                message.setText(setReviewDescription(chatId, messageText, reviewId));
                return message;

            case EDIT_REVIEW_BUTTON_PLACE:
                message.setText(setReviewPlace(chatId, messageText, reviewId));
                return message;

            case EDIT_REVIEW_BUTTON_TITLE:
                message.setText(setReviewTittle(chatId, messageText, reviewId));
                return message;
            case EDIT_REVIEW_BUTTON_LIMIT:
                message.setText(setReviewLimit(chatId, messageText, reviewId));
                return message;
            default:
                message.setText(INDEV_TEXT);
                return message;

        }
    }

    private String setReviewLimit(Long chatId, String messageText, Long reviewId) {
        int userLimit;
        try {
            userLimit = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return INCORRECT_NUMBER_ANS;
        }
        reviewRepository.setUserLimitByOwnerIdAndReviewId(userLimit, chatId, reviewId);
        sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
        sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
        return CHANGED_REVIEW_LIMIT_TEXT;
    }

    private String setReviewCafeType(long chatId, String messageText, Long reviewId) {
        if (isValidName(messageText)) {
            reviewRepository.setCafeTypeByOwnerIdAndReviewId(messageText, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_ANIMAL_TYPE_TEXT;
        } else {
            return INCORRECT_REVIEW_CAFE_TYPE;
        }
    }

    private String setReviewData(long chatId, String dateString, Long reviewId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STYLE);
        Date currentDate = new Date();
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(dateString);

            if (parsedDate.before(currentDate)) {
                return REVIEW_DATE_TEXT;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (month > 12 || day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                return REVIEW_DATE_TEXT;
            }

            Timestamp eventDate = new Timestamp(parsedDate.getTime());
            reviewRepository.setVisitDateByOwnerIdAndReviewId(eventDate, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_DATA_TEXT;

        } catch (ParseException e) {
            e.printStackTrace();
            return REVIEW_DATE_TEXT;
        }
    }

    private String setReviewForename(long chatId, String messageText, Long reviewId) {
        if (isValidName(messageText)) {
            reviewRepository.setForenameByOwnerIdAndReviewId(messageText, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_BREED_TEXT;

        } else {
            return INCORRECT_REVIEW_FORENAME;
        }
    }

    private String setReviewDescription(long chatId, String messageText, Long reviewId) {
        if (isValidName(messageText)) {
            reviewRepository.setDescriptionByOwnerIdAndReviewId(messageText, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_DESCRIPTION_TEXT;

        } else {
            return INCORRECT_REVIEW_DESCRIPTION;
        }
    }

    private String setReviewPlace(long chatId, String messageText, Long reviewId) {
        if (isValidName(messageText)) {
            reviewRepository.setPlaceByOwnerIdAndReviewId(messageText, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_PLACE_TEXT;

        } else {
            return INCORRECT_REVIEW_PLACE;
        }
    }

    private String setReviewTittle(long chatId, String messageText, Long reviewId) {
        if (isValidName(messageText)) {
            reviewRepository.setTitleByOwnerIdAndReviewId(messageText, chatId, reviewId);
            sessionRepository.setEditReviewFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setNumberEditReviewByChatId(chatId, 0L);
            return CHANGED_REVIEW_TITTLE_TEXT;

        } else {
            return INCORRECT_REVIEW_TITTLE;
        }

    }


}


