package org.calculator.costcalculation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlankPriceRequest {
    private String species;
    private Number outerDiameter;
    private String diameter;
    private String steelGrade;
    private Number quantity;
}
