package org.example.CoffeeBewerter.repository;

import org.example.CoffeeBewerter.model.Cafe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryCafe.*;

public interface CafeRepository extends CrudRepository<Cafe, Long> {

    Cafe findByCafeIdAndOwnerId(Long cafeId, Long ownerId);

    ArrayList<Cafe> findAllByOwnerId(Long ownerId);

    ArrayList<Cafe> findAll();

    @Transactional
    void deleteByOwnerId(Long ownerId);

    @Transactional
    void deleteByOwnerIdAndCafeId(Long ownerId, Long cafeId);


    @Query(FIND_TOP_BY_ORDER_BY_VISITOR_ID_DESC)
    Long findTopByOrderByOwnerIdDesc(@Param(PARAM_VISITOR_ID) Long ownerId);

    @Query(FIND_TOP_BY_ORDER_DESC)
    Long findTopByOrderDesc();

    @Modifying
    @Transactional
    @Query(SET_CAFE_NOTE_BY_VISITOR_ID_AND_CAFE_ID)
    void setCafeNameByOwnerIdAndCafeId(String cafename, Long ownerId, Long cafeId);

    @Modifying
    @Transactional
    @Query(SET_CAFE_TYPE_BY_VISITOR_ID_AND_CAFE_ID)
    void setCafeTypeByOwnerIdAndCafeId(String cafetype, Long ownerId, Long cafeId);

    @Modifying
    @Transactional
    @Query(SET_CAFE_FORENAME_BY_VISITOR_ID_AND_CAFE_ID)
    void setCafeForenameByOwnerIdAndCafeId(String cafeforename, Long ownerId, Long cafeId);


}


