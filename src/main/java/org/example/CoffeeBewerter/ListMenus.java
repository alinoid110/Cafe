package org.example.CoffeeBewerter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static org.example.CoffeeBewerter.constants.keyboardsConstants.ListMenusConstants.*;

@Slf4j
@Component
public class ListMenus {

    public InlineKeyboardMarkup profileButtonKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileEditRows = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow1 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow2 = new ArrayList<>();

        var editPofileButton = new InlineKeyboardButton();

        editPofileButton.setText(EDIT_PROFILE_BUTTON_TEXT);
        editPofileButton.setCallbackData(EDIT_PROFILE);

        var deletePofileButton = new InlineKeyboardButton();

        deletePofileButton.setText(DELETE_PROFILE_BUTTON_TEXT);
        deletePofileButton.setCallbackData(DELETE_PROFILE_QUESTION);

        profileEditRow1.add(editPofileButton);
        profileEditRow2.add(deletePofileButton);
        profileEditRows.add(profileEditRow1);
        profileEditRows.add(profileEditRow2);
        profileButtonKeyboard.setKeyboard(profileEditRows);

        return profileButtonKeyboard;
    }

    public InlineKeyboardMarkup profileDeleteKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileDelRows = new ArrayList<>();
        List<InlineKeyboardButton> profileDelRow1 = new ArrayList<>();

        var editPofileButton = new InlineKeyboardButton();

        editPofileButton.setText(DELETE_PROFILE_CONFIRM_TEXT);
        editPofileButton.setCallbackData(DELETE_PROFILE_CONFIRM);

        var deletePofileButton = new InlineKeyboardButton();

        deletePofileButton.setText(DELETE_PROFILE_DENY_TEXT);
        deletePofileButton.setCallbackData(DELETE_PROFILE_DENY);

        profileDelRow1.add(editPofileButton);
        profileDelRow1.add(deletePofileButton);
        profileDelRows.add(profileDelRow1);
        profileButtonKeyboard.setKeyboard(profileDelRows);

        return profileButtonKeyboard;
    }

    public InlineKeyboardMarkup profileEditKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileEditRows = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow1 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow2 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow3 = new ArrayList<>();

        var editProfileButtonName = new InlineKeyboardButton();

        editProfileButtonName.setText(EDIT_PROFILE_NAME_TEXT);
        editProfileButtonName.setCallbackData(EDIT_PROFILE_NAME);

        profileEditRow1.add(editProfileButtonName);
        profileEditRows.add(profileEditRow1);

        var editProfileButtonLastName = new InlineKeyboardButton();

        editProfileButtonLastName.setText(EDIT_PROFILE_LASTNAME_TEXT);
        editProfileButtonLastName.setCallbackData(EDIT_PROFILE_LASTNAME);

        profileEditRow2.add(editProfileButtonLastName);
        profileEditRows.add(profileEditRow2);

        var editProfileButtonPhoneNumber = new InlineKeyboardButton();

        editProfileButtonPhoneNumber.setText(EDIT_PROFILE_PHONE_NUMBER_TEXT);
        editProfileButtonPhoneNumber.setCallbackData(EDIT_PROFILE_PHONE_NUMBER);

        profileEditRow3.add(editProfileButtonPhoneNumber);
        profileEditRows.add(profileEditRow3);


        profileButtonKeyboard.setKeyboard(profileEditRows);

        return profileButtonKeyboard;
    }

    public InlineKeyboardMarkup cafeQuestionButtonKeyboard() {
        InlineKeyboardMarkup cafeQuestionKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> cafeQuestionRows = new ArrayList<>();
        List<InlineKeyboardButton> cafeQuestionRow1 = new ArrayList<>();


        var cafeQuestionButtonConfirm = new InlineKeyboardButton();

        cafeQuestionButtonConfirm.setText(CAFE_QUESTION_CONFIRM_BUTTON_TEXT);
        cafeQuestionButtonConfirm.setCallbackData(CAFE_QUESTION_CONFIRM);

        var cafeQuestionButtonDeny = new InlineKeyboardButton();

        cafeQuestionButtonDeny.setText(CAFE_QUESTION_DENY_TEXT);
        cafeQuestionButtonDeny.setCallbackData(CAFE_QUESTION_DENY);

        cafeQuestionRow1.add(cafeQuestionButtonConfirm);
        cafeQuestionRow1.add(cafeQuestionButtonDeny);
        cafeQuestionRows.add(cafeQuestionRow1);
        cafeQuestionKeyboard.setKeyboard(cafeQuestionRows);

        return cafeQuestionKeyboard;
    }

    public InlineKeyboardMarkup cafeAddButtonKeyboard() {
        InlineKeyboardMarkup cafeAddKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> cafeAddRows = new ArrayList<>();
        List<InlineKeyboardButton> cafeAddRow1 = new ArrayList<>();


        var cafeAddButton = new InlineKeyboardButton();

        cafeAddButton.setText(CAFE_ADD_TEXT);
        cafeAddButton.setCallbackData(CAFE_ADD);

        var cafeDeleteButton = new InlineKeyboardButton();

        cafeDeleteButton.setText(CAFE_DELETE_SELECTION_TEXT);
        cafeDeleteButton.setCallbackData(CAFE_DELETE_SELECTION);


        cafeAddRow1.add(cafeDeleteButton);
        cafeAddRow1.add(cafeAddButton);
        cafeAddRows.add(cafeAddRow1);
        cafeAddKeyboard.setKeyboard(cafeAddRows);

        return cafeAddKeyboard;
    }

    public InlineKeyboardMarkup reviewKeyboard() {
        InlineKeyboardMarkup reviewCreateKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> reviewCreateRows = new ArrayList<>();
        List<InlineKeyboardButton> reviewCreateRow = new ArrayList<>();

        var reviewCreateButton = new InlineKeyboardButton();

        reviewCreateButton.setText(REVIEW_ADD_TEXT);
        reviewCreateButton.setCallbackData(REVIEW_ADD);

        reviewCreateRow.add(reviewCreateButton);
        reviewCreateRows.add(reviewCreateRow);
        reviewCreateKeyboard.setKeyboard(reviewCreateRows);

        return reviewCreateKeyboard;
    }

    public ReplyKeyboard createdReviewKeyboard() {
        InlineKeyboardMarkup createdReviewKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> createdReviewRows = new ArrayList<>();
        List<InlineKeyboardButton> showInfoCreatedReviewRow = new ArrayList<>();
        List<InlineKeyboardButton> createAndDeleteAndEditReviewRow = new ArrayList<>();

        var showInfoCreatedReviewButton = new InlineKeyboardButton();
        var createReviewButton = new InlineKeyboardButton();
        var deleteReviewButton = new InlineKeyboardButton();
        var editReviewButton = new InlineKeyboardButton();

        showInfoCreatedReviewButton.setText(CREATED_REVIEW_FULL_INFO_TEXT);
        showInfoCreatedReviewButton.setCallbackData(CREATED_REVIEW_FULL_INFO_SELECT);

        createReviewButton.setText(REVIEW_ADD_TEXT);
        createReviewButton.setCallbackData(REVIEW_ADD);

        deleteReviewButton.setText(REVIEW_DELETE_BUTTON_TEXT);
        deleteReviewButton.setCallbackData(REVIEW_DELETE_BUTTON_SELECT);

        editReviewButton.setText(REVIEW_EDIT_BUTTON_TEXT);
        editReviewButton.setCallbackData(REVIEW_EDIT_BUTTON_SELECT);


        showInfoCreatedReviewRow.add(showInfoCreatedReviewButton);
        createAndDeleteAndEditReviewRow.add(createReviewButton);
        createAndDeleteAndEditReviewRow.add(deleteReviewButton);
        createAndDeleteAndEditReviewRow.add(editReviewButton);

        createdReviewRows.add(showInfoCreatedReviewRow);
        createdReviewRows.add(createAndDeleteAndEditReviewRow);
        createdReviewKeyboard.setKeyboard(createdReviewRows);

        return createdReviewKeyboard;
    }

    public InlineKeyboardMarkup reviewEditKeyboard() {
        InlineKeyboardMarkup reviewEditKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> reviewEditRows = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditTitleRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditDataRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditPlaceRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditCafeTypeRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditForenameRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditDescriptionRow = new ArrayList<>();
        List<InlineKeyboardButton> reviewEditLimitRow = new ArrayList<>();


        var editReviewButtonTitle = new InlineKeyboardButton();

        editReviewButtonTitle.setText(EDIT_REVIEW_BUTTON_TITLE_TEXT);
        editReviewButtonTitle.setCallbackData(EDIT_REVIEW_BUTTON_TITLE);

        reviewEditTitleRow.add(editReviewButtonTitle);
        reviewEditRows.add(reviewEditTitleRow);

        var editReviewButtonData = new InlineKeyboardButton();

        editReviewButtonData.setText(EDIT_REVIEW_BUTTON_DATE_TEXT);
        editReviewButtonData.setCallbackData(EDIT_REVIEW_BUTTON_DATE);

        reviewEditDataRow.add(editReviewButtonData);
        reviewEditRows.add(reviewEditDataRow);

        var editReviewButtonPlace = new InlineKeyboardButton();

        editReviewButtonPlace.setText(EDIT_REVIEW_BUTTON_PLACE_TEXT);
        editReviewButtonPlace.setCallbackData(EDIT_REVIEW_BUTTON_PLACE);

        reviewEditPlaceRow.add(editReviewButtonPlace);
        reviewEditRows.add(reviewEditPlaceRow);

        var editReviewButtonCafeType = new InlineKeyboardButton();

        editReviewButtonCafeType.setText(EDIT_REVIEW_BUTTON_CAFE_TYPE_TEXT);
        editReviewButtonCafeType.setCallbackData(EDIT_REVIEW_BUTTON_CAFE_TYPE);

        reviewEditCafeTypeRow.add(editReviewButtonCafeType);
        reviewEditRows.add(reviewEditCafeTypeRow);

        var editReviewButtonBreed = new InlineKeyboardButton();
        editReviewButtonBreed.setText(EDIT_REVIEW_BUTTON_BREED_TEXT);
        editReviewButtonBreed.setCallbackData(EDIT_REVIEW_BUTTON_FORENAME);

        reviewEditForenameRow.add(editReviewButtonBreed);
        reviewEditRows.add(reviewEditForenameRow);


        var editReviewButtonDescription = new InlineKeyboardButton();
        editReviewButtonDescription.setText(EDIT_REVIEW_BUTTON_DESCRIPTION_TEXT);
        editReviewButtonDescription.setCallbackData(EDIT_REVIEW_BUTTON_DESCRIPTION);

        reviewEditDescriptionRow.add(editReviewButtonDescription);
        reviewEditRows.add(reviewEditDescriptionRow);

        var editReviewButtonUserLimit = new InlineKeyboardButton();
        editReviewButtonUserLimit.setText(EDIT_REVIEW_BUTTON_LIMIT_TEXT);
        editReviewButtonUserLimit.setCallbackData(EDIT_REVIEW_BUTTON_LIMIT);

        reviewEditLimitRow.add(editReviewButtonUserLimit);
        reviewEditRows.add(reviewEditLimitRow);

        reviewEditKeyboard.setKeyboard(reviewEditRows);

        return reviewEditKeyboard;
    }

    public ReplyKeyboard appliedReviewKeyboard() {
        InlineKeyboardMarkup appliedReviewKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> appliedReviewRows = new ArrayList<>();
        List<InlineKeyboardButton> appliedReviewRow = new ArrayList<>();


        var showInfoAppliedReviewButton = new InlineKeyboardButton();

        showInfoAppliedReviewButton.setText(APPLIED_REVIEW_FULL_INFO_TEXT);
        showInfoAppliedReviewButton.setCallbackData(APPLIED_REVIEW_FULL_INFO_SELECT);


        appliedReviewRow.add(showInfoAppliedReviewButton);
        appliedReviewRows.add(appliedReviewRow);
        appliedReviewKeyboard.setKeyboard(appliedReviewRows);

        return appliedReviewKeyboard;
    }

    public ReplyKeyboard searchReviewKeyboard() {
        InlineKeyboardMarkup searchReviewKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> searchReviewRows = new ArrayList<>();
        List<InlineKeyboardButton> searchReviewRow = new ArrayList<>();

        var likeReviewButton = new InlineKeyboardButton();
        var dislikeReviewButton = new InlineKeyboardButton();
        var pinnedReviewButton = new InlineKeyboardButton();

        likeReviewButton.setText(LIKE_REVIEW_TEXT);
        likeReviewButton.setCallbackData(LIKE_REVIEW);

        dislikeReviewButton.setText(DISLIKE_REVIEW_TEXT);
        dislikeReviewButton.setCallbackData(DISLIKE_REVIEW);

        pinnedReviewButton.setText(STOP_REVIEW_TEXT);
        pinnedReviewButton.setCallbackData(STOP_REVIEW);

        searchReviewRow.add(likeReviewButton);
        searchReviewRow.add(dislikeReviewButton);
//        searchReviewRow.add(pinnedReviewButton); Заготовка кнопки отложить TODO
        searchReviewRows.add(searchReviewRow);
        searchReviewKeyboard.setKeyboard(searchReviewRows);

        return searchReviewKeyboard;
    }


    public InlineKeyboardMarkup registrationKeyboard() {
        InlineKeyboardMarkup registrationKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> registrationRows = new ArrayList<>();
        List<InlineKeyboardButton> registrationRow = new ArrayList<>();

        var registrationButton = new InlineKeyboardButton();

        registrationButton.setText(REGISTRATION_TEXT);
        registrationButton.setCallbackData(REGISTRATION);

        registrationRow.add(registrationButton);
        registrationRows.add(registrationRow);
        registrationKeyboard.setKeyboard(registrationRows);

        return registrationKeyboard;
    }

}
