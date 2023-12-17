package org.example.CoffeeBewerter.repository;

import org.example.CoffeeBewerter.model.UserSession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.example.CoffeeBewerter.constants.queryConstants.QuerySession.*;

public interface SessionRepository extends CrudRepository<UserSession, Long> {

    UserSession findByChatId(Long chatId);


    @Modifying
    @Transactional
    @Query(SET_GLOBAL_CONTEXT_BY_CHAT_ID)
    void setGlobalContextByChatId(String globalContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_REG_USER_CONTEXT_BY_CHAT_ID)
    void setRegUserContextByChatId(String regUserContext, Long chatId);

    @Transactional
    void deleteByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query(SET_EDIT_USER_CONTEXT_BY_CHAT_ID)
    void setEditUserContextByChatId(String EditUserContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_CAFE_REGISTER_FUNCTION_CONTEXT)
    void setCafeRegisterFunctionContext(String CafeRegisterFunctionContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_REVIEW_REGISTER_FUNCTION_CONTEXT)
    void setReviewRegisterFunctionContext(String ReviewRegisterFunctionContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_REVIEW_REGISTER_FUNCTION_ID)
    void setReviewRegisterFunctionId(Long ReviewRegisterFunctionId, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_EDIT_REVIEW_FUNCTION_CONTEXT_BY_CHAT_ID)
    void setEditReviewFunctionContextByChatId(String EditReviewContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_NUMBER_EDIT_REVIEW_BY_CHAT_ID)
    void setNumberEditReviewByChatId(Long NumberEditReview, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_NUMBER_SEARCH_REVIEW_BY_CHAT_ID)
    void setNumberSearchRevewByChatId(Long NumberEditReview, Long chatId);



}
