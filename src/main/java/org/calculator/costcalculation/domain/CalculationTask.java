package org.calculator.costcalculation.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalculationTask {
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

    private String status;
    private Double result;
    private String materialSelect;
}
