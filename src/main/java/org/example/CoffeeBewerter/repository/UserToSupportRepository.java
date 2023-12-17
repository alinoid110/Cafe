package org.example.CoffeeBewerter.repository;

import org.example.CoffeeBewerter.model.UserToSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryUserToSupport.FIND_TOP_BY_ORDER_BY_ID_DESC;

public interface UserToSupportRepository extends CrudRepository<UserToSupport, Long> {

    UserToSupport findByChatIdAndReviewId(Long chatId, Long reviewId);

    ArrayList<UserToSupport> findAllByChatId(Long chatId);

    ArrayList<UserToSupport> findAllByReviewId(Long reviewId);

    @Transactional
    Long countByReviewId(Long reviewId);

    @Query(FIND_TOP_BY_ORDER_BY_ID_DESC)
    Long findTopByOrderByIdDesc();

    @Transactional
    void deleteAllByChatId(Long chatId);

    @Transactional
    void deleteByChatIdAndReviewId(Long chatId, Long reviewId);

    @Transactional
    void deleteAllByReviewId(Long reviewId);

}
