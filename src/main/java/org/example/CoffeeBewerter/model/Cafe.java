package org.example.CoffeeBewerter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.example.CoffeeBewerter.constants.queryConstants.QueryCafe.ENTITY_NAME_CAFE;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_CAFE)
public class Cafe {

    @Id
    private Long cafeId;

    private String cafeNote;

    private String cafeType;

    private String cafeForename;

    private String cafeImageId;

    private Long ownerId;


}
