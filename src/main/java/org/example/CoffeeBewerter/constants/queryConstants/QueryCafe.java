package org.example.CoffeeBewerter.constants.queryConstants;

public class QueryCafe {
    public static final String ENTITY_NAME_CAFE = "cafeDataTable";
    public static final String FIND_TOP_BY_ORDER_BY_VISITOR_ID_DESC = "select max(cafe.cafeId) from cafeDataTable cafe where cafe.ownerId = :ownerId";
    public static final String PARAM_VISITOR_ID = "ownerId";
    public static final String FIND_TOP_BY_ORDER_DESC = "select max(cafe.cafeId) from cafeDataTable cafe";
    public static final String SET_CAFE_NOTE_BY_VISITOR_ID_AND_CAFE_ID = "update cafeDataTable u set u.cafeName = ?1 where u.ownerId = ?2 and u.cafeId = ?3";
    public static final String SET_CAFE_TYPE_BY_VISITOR_ID_AND_CAFE_ID = "update cafeDataTable u set u.cafeType = ?1 where u.ownerId = ?2 and u.cafeId = ?3";
    public static final String SET_CAFE_FORENAME_BY_VISITOR_ID_AND_CAFE_ID = "update cafeDataTable u set u.cafeForename = ?1 where u.ownerId = ?2 and u.cafeId = ?3";

}
