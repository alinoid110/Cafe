package org.example.CoffeeBewerter.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryUserToSupport.ENTITY_NAME_USER_TO_REVIEW;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_USER_TO_REVIEW)
public class UserToSupport {

    @Id
    private Long id;
    private Long chatId;
    private Long reviewId;

}
