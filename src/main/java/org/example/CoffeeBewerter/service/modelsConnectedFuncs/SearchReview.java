package org.example.CoffeeBewerter.service.modelsConnectedFuncs;

import org.example.CoffeeBewerter.KeyboardMenus;
import org.example.CoffeeBewerter.ListMenus;
import org.example.CoffeeBewerter.model.Review;
import org.example.CoffeeBewerter.model.UserToSupport;
import org.example.CoffeeBewerter.repository.ReviewRepository;
import org.example.CoffeeBewerter.repository.CafeRepository;
import org.example.CoffeeBewerter.repository.SessionRepository;
import org.example.CoffeeBewerter.repository.UserToSupportRepository;
import org.example.CoffeeBewerter.service.generalFuncs.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.example.CoffeeBewerter.constants.funcsConstants.ReviewFuncsConstants.*;
import static org.example.CoffeeBewerter.constants.funcsConstants.ShowReviewConstants.NO_REMAINING_REVIEW_TEXT;

@Slf4j
@Component
public class SearchReview {

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
    private ReviewFuncs reviewFuncs;
    @Autowired
    private FileService fileService;
    @Autowired
    private CafeRepository cafeRepository;

    public String showAllReviewInfo(Review review) {
        String fullFilledStatus = review.getFullFilled() ? YES_FOR_MSG : NO_FOR_MSG;
        String completedStatus = review.getCompleted() ? YES_FOR_MSG : NO_FOR_MSG;
        String created_review_item = REVIEW_ID_FOR_MSG + review.getReviewId() + "\n\n" +
                REVIEW_NAME_FOR_MSG + review.getTitle() + "\n\n" +
                REVIEW_DATE_FOR_MSG + review.getVisitDate() + "\n\n" +
                REVIEW_CAFE_TYPE_FOR_MSG + review.getCafeType() + "\n\n" +
                REVIEW_CAFE_FORENAME_FOR_MSG + review.getForename() + "\n\n" +
                REVIEW_DESCRIPTION_FOR_MSG + review.getDescription() + "\n\n" +
                REVIEW_PLACE_FOR_MSG + review.getPlace() + "\n\n" +
                REVIEW_MAX_PARTICIPANTS_AMOUNT_FOR_MSG + review.getUserLimit() + "\n\n" +
                REVIEW_FULFILLED_FOR_MSG + fullFilledStatus + "\n\n" +
                REVIEW_HAS_ENDED_FOR_MSG + completedStatus + "\n\n";
        return created_review_item;
    }

    private boolean shouldReturnNull() {
        Random random = new Random();
        return random.nextInt(10) == 0;
    }

    public File showRandomCafe() throws IOException {
        if (!shouldReturnNull()) {
            return null;
        }

        Random random = new Random();

        return fileService.giveCaffePhotoByFilePath((long) random.nextInt(10));
    }

    public SendMessage searchReview(long chatId) {

        if (userFuncs.checkExistingProfile(chatId) != null) {
            return userFuncs.checkExistingProfile(chatId);
        }

        ArrayList<Review> all_reviews_for_searches = reviewRepository.findMyNotAppliedReview(chatId);
        System.out.println(all_reviews_for_searches);
        long numberSearchReviewId = sessionRepository.findByChatId(chatId).getNumberSearchReview() + 1;
        SendMessage message = new SendMessage();

        if (all_reviews_for_searches.isEmpty()) {
            message.setText(NO_REMAINING_REVIEW_TEXT);
        } else {
            if (numberSearchReviewId >= all_reviews_for_searches.size()) {
                numberSearchReviewId = 0;
                sessionRepository.setNumberSearchRevewByChatId(numberSearchReviewId, chatId);
            }

            Review review = all_reviews_for_searches.get(Math.toIntExact(numberSearchReviewId));
            sessionRepository.setNumberSearchRevewByChatId(numberSearchReviewId, chatId);
            message.setText(showAllReviewInfo(review));
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.searchReviewKeyboard());
        }
        return message;

    }

    public SendMessage likeReview(long chatId) {
        UserToSupport userToSupport = new UserToSupport();
        if (userToSupportRepository.findTopByOrderByIdDesc() != null) {
            Long Id = userToSupportRepository.findTopByOrderByIdDesc();
            userToSupport.setId(Id + 1L);
        } else {
            Long Id = 1L;
            userToSupport.setId(Id);
        }

        long numberSearchReviewId = sessionRepository.findByChatId(chatId).getNumberSearchReview();
        ArrayList<Review> all_reviews_for_searches = reviewRepository.findMyNotAppliedReview(chatId);

        Review review = all_reviews_for_searches.get(Math.toIntExact(numberSearchReviewId));

        userToSupport.setReviewId(review.getReviewId());
        userToSupport.setChatId(chatId);
        userToSupportRepository.save(userToSupport);

        return searchReview(chatId);

    }


}
