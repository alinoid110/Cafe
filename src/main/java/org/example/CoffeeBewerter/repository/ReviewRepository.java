package org.example.CoffeeBewerter.repository;

import org.example.CoffeeBewerter.model.Review;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryReview.*;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    Review findByReviewIdAndOwnerId(Long reviewId, Long ownerId);

    Review findByReviewId(Long reviewId);

    ArrayList<Review> findAllByOwnerId(Long ownerId);

    ArrayList<Review> findAllByOwnerIdNot(Long ownerId);

    ArrayList<Review> findAll();

    ArrayList<Review> findAllByCafeType(String cafeType);

    @Modifying
    @Transactional
    @Query(FIND_MY_APPLIED_REVIEW)
    ArrayList<Review> findMyAppliedReview(@Param(DOP_CHAT_ID) Long chatId);


    @Modifying
    @Transactional
    @Query(FIND_MY_NOT_APPLIED_REVIEW)
    ArrayList<Review> findMyNotAppliedReview(@Param(DOP_CHAT_ID) Long chatId);

    @Transactional
    void deleteAllByOwnerId(Long ownerId);

    @Transactional
    void deleteAllByReviewId(Long reviewId);

    @Transactional
    void deleteByOwnerIdAndReviewId(Long ownerId, Long reviewId);


    @Query(FIND_TOP_BY_ORDER_BY_REVIEW_ID_DESC)
    Long findTopByOrderByReviewIdDesc();


    @Modifying
    @Transactional
    @Query(SET_CAFE_TYPE_BY_VISITOR_ID_AND_REVIEW_ID)
    void setCafeTypeByOwnerIdAndReviewId(String cafeType, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_FORENAME_BY_VISITOR_ID_AND_REVIEW_ID)
    void setForenameByOwnerIdAndReviewId(String forename, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_TITLE_BY_VISITOR_ID_AND_REVIEW_ID)
    void setTitleByOwnerIdAndReviewId(String title, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_DESCRIPTION_BY_VISITOR_ID_AND_REVIEW_ID)
    void setDescriptionByOwnerIdAndReviewId(String description, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_PLACE_BY_VISITOR_ID_AND_REVIEW_ID)
    void setPlaceByOwnerIdAndReviewId(String place, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_COMPLETED_BY_VISITOR_ID_AND_REVIEW_ID)
    void setCompletedByOwnerIdAndReviewId(Boolean completed, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_FULFILLED_BY_VISITOR_ID_AND_REVIEW_ID)
    void setFullFilledByOwnerIdAndReviewId(Boolean fullFilled, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_USER_SUPPORT_BY_VISITOR_ID_AND_REVIEW_ID)
    void setUserLimitByOwnerIdAndReviewId(Integer userLimit, Long ownerId, Long reviewId);

    @Modifying
    @Transactional
    @Query(SET_DATE_BY_VISITOR_ID_AND_REVIEW_ID)
    void setVisitDateByOwnerIdAndReviewId(Timestamp visitDate, Long ownerId, Long reviewId);


}
