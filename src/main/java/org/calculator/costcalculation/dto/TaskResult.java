package org.calculator.costcalculation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TaskResult {
    private String orderId;
    private String species;
    private String steelGrade;
    private Double pricePerKg;
    private Double blankWeightKg;
    private Double blankLengthKg;
    private BigDecimal totalPrice;
}
