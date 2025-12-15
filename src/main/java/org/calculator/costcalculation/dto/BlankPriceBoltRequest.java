package org.calculator.costcalculation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlankPriceBoltRequest {
    private String species;
    private String length;
    private String diameter;
    private String steelGrade;
    private Number quantity;
    private Number sawCut;
    private String stateStandard;
}
