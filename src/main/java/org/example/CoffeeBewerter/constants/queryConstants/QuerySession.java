package org.example.CoffeeBewerter.constants.queryConstants;

public class QuerySession {

    public static final String ENTITY_NAME_USER_SESSION = "sessionDataTable";
    public static final String DEFAULT_CONTEXT_STATE_VALUE = "default";
    public static final String SET_GLOBAL_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.globalFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_REG_USER_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.registerFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_EDIT_USER_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.editFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_CAFE_REGISTER_FUNCTION_CONTEXT = "update sessionDataTable u set u.cafeRegisterFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_REVIEW_REGISTER_FUNCTION_CONTEXT = "update sessionDataTable u set u.reviewRegisterFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_REVIEW_REGISTER_FUNCTION_ID = "update sessionDataTable u set u.reviewRegisterFunctionId = ?1 where u.chatId = ?2";
    public static final String SET_EDIT_REVIEW_FUNCTION_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.editReviewFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_NUMBER_EDIT_REVIEW_BY_CHAT_ID = "update sessionDataTable u set u.numberEditReview = ?1 where u.chatId = ?2";
    public static final String SET_NUMBER_SEARCH_REVIEW_BY_CHAT_ID = "update sessionDataTable u set u.numberSearchReview = ?1 where u.chatId = ?2";

}
