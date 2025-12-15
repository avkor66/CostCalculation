package org.calculator.costcalculation.dto;

import java.util.List;

public class ApiResult {
    private final String message;
    private final List<TaskResult> orders;

    public ApiResult(String message, List<TaskResult> orders) {
        this.message = message;
        this.orders = orders;
    }

    public String getMessage() { return message; }
    public List<TaskResult> getOrders() { return orders; }
}
