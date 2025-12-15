package org.calculator.costcalculation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlankPriceNutRequest {
    private String species;
    private String diameter;
    private String steelGrade;
    private Number quantity;
    private Number threadPitch;
    private Number height;
    private Number sawCut;
    private String stateStandard;
}
