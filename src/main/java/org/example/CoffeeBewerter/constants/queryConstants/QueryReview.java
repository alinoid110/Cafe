package org.example.CoffeeBewerter.constants.queryConstants;

public class QueryReview {
    public static final String ENTITY_NAME_REVIEW = "reviewDataTable";
    public static final String FIND_MY_APPLIED_REVIEW = "SELECT m FROM reviewDataTable m " +
            "JOIN userToReviewDataTable utm ON m.reviewId = utm.reviewId " +
            "WHERE utm.chatId = :chatId AND utm.chatId != m.ownerId";

    public static final String FIND_MY_NOT_APPLIED_REVIEW = "SELECT m FROM reviewDataTable m " +
            "JOIN userToReviewDataTable utm ON m.reviewId = utm.reviewId " +
            "WHERE utm.chatId != :chatId AND utm.chatId = m.ownerId " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM userToReviewDataTable utm2 " +
            "   WHERE utm2.reviewId = m.reviewId AND utm2.chatId = :chatId" +
            ")";
    public static final String DOP_CHAT_ID = "chatId";
    public static final String FIND_TOP_BY_ORDER_BY_REVIEW_ID_DESC = "select max(review.reviewId) from reviewDataTable review";
    public static final String SET_CAFE_TYPE_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.cafeType = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_FORENAME_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.forename = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_TITLE_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.title = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_DESCRIPTION_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.description = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_PLACE_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.place = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_COMPLETED_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.completed = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_FULFILLED_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.fullFilled = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_USER_SUPPORT_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.userLimit = ?1 where u.ownerId = ?2 and u.reviewId = ?3";
    public static final String SET_DATE_BY_VISITOR_ID_AND_REVIEW_ID = "update reviewDataTable u set u.visitDate = ?1 where u.ownerId = ?2 and u.reviewId = ?3";

}
