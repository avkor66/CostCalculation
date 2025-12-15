package org.calculator.costcalculation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlankPriceWasherRequest {
    private String species;
    private Number outerDiameter;
    private String diameter;
    private String steelGrade;
    private Number quantity;
    private Number height;
    private Number sawCut;
    private String stateStandard;
}

