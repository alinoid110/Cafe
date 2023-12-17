package org.example.CoffeeBewerter.constants.queryConstants;

public class QueryUserToSupport {

    public static final String ENTITY_NAME_USER_TO_REVIEW = "userToReviewDataTable";
    public static final String FIND_TOP_BY_ORDER_BY_ID_DESC = "select max(element.id) from userToReviewDataTable element";

}
