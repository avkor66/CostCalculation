package org.calculator.costcalculation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationRequest {
    private String cartId;
    private Number height;
    private Number outerDiameter;
    private Number innerDiameter;
    private String species;
    private String stateStandard;
    private String diameter;
    private String length;
    private String threadLength;
    private String steelGrade;
    private String execution;
    private Number quantity;
    private Boolean delivery;
    private Number volume;
    private String comment;

}
