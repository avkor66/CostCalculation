package org.calculator.costcalculation.dto;

public class ApiResponse {
    private String message;
    private String cartId;

    public ApiResponse(String message, String cartId) {
        this.message = message;
        this.cartId = cartId;
    }

    public String getMessage() { return message; }
    public String getCartId() { return cartId; }
}