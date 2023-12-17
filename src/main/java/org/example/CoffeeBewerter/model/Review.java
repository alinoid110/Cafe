package org.example.CoffeeBewerter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryReview.ENTITY_NAME_REVIEW;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_REVIEW)
public class Review {

    @Id
    private Long reviewId;

    private String cafeType;

    private String forename;

    private String title;

    private String description;

    private String place;

    private Boolean completed;

    private Boolean fullFilled;

    private Integer userLimit;

    private Long ownerId;

    private Timestamp visitDate;

}
