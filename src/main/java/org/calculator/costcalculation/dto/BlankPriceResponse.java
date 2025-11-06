package org.calculator.costcalculation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlankPriceResponse {
    private String species;
    private String steelGrade;
    private Double pricePerKg;
    private Double blankWeightKg;
    private Double blankLengthKg;
    private Double totalPrice;
}
